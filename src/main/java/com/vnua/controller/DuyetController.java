package com.vnua.controller;

import com.vnua.model.*;
import com.vnua.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DuyetController {

    @Autowired private PublicationService publicationService;
    @Autowired private ProjectService projectService;
    @Autowired private ConferenceService conferenceService;
    @Autowired private BookService bookService;
    @Autowired private PatentService patentService;
    @Autowired private SupervisionService supervisionService;

    @GetMapping("/duyet")
    public String duyetPage(Model model, HttpSession session) {
        SysUser user = (SysUser) session.getAttribute("loggedInUser");
        if (user == null || user.getUserType() != 99) {
            return "redirect:/login";
        }

        model.addAttribute("pendingPublications", publicationService.getByStatus(0));
        model.addAttribute("pendingProjects", projectService.getByStatus(0));
        model.addAttribute("pendingConferences", conferenceService.getByStatus(0));
        model.addAttribute("pendingBooks", bookService.getByStatus(0));
        model.addAttribute("pendingPatents", patentService.getByStatus(0));
        model.addAttribute("pendingSupervisions", supervisionService.getByStatus(0));

        return "duyet";
    }

    @PostMapping("/duyet/approve/{type}/{id}")
    public String approve(
            @PathVariable String type,
            @PathVariable int id,
            RedirectAttributes redirectAttributes,
            HttpSession session
    ) {
        checkPermission(session);
        approveItem(type, id, 1);
        redirectAttributes.addFlashAttribute("success", "✅ Đã duyệt " + getTypeName(type) + " ID: " + id);
        return "redirect:/duyet";
    }

    @PostMapping("/duyet/reject/{type}/{id}")
    public String reject(
            @PathVariable String type,
            @PathVariable int id,
            RedirectAttributes redirectAttributes,
            HttpSession session
    ) {
        checkPermission(session);
        approveItem(type, id, 2);
        redirectAttributes.addFlashAttribute("success", "❌ Đã từ chối " + getTypeName(type) + " ID: " + id);
        return "redirect:/duyet";
    }

    private void checkPermission(HttpSession session) {
        SysUser user = (SysUser) session.getAttribute("loggedInUser");
        if (user == null || user.getUserType() != 99) {
            throw new RuntimeException("Không có quyền");
        }
    }

    private void approveItem(String type, int id, int status) {
        switch (type) {
            case "publication": publicationService.updateStatus(id, status); break;
            case "project": projectService.updateStatus(id, status); break;
            case "conference": conferenceService.updateStatus(id, status); break;
            case "book": bookService.updateStatus(id, status); break;
            case "patent": patentService.updateStatus(id, status); break;
            case "supervision": supervisionService.updateStatus(id, status); break;
            default: throw new IllegalArgumentException("Loại không hợp lệ: " + type);
        }
    }

    private String getTypeName(String type) {
        return switch (type) {
            case "publication" -> "bài báo";
            case "project" -> "đề tài";
            case "conference" -> "hội thảo";
            case "book" -> "sách";
            case "patent" -> "bằng sáng chế";
            case "supervision" -> "hướng dẫn";
            default -> type;
        };
    }
}