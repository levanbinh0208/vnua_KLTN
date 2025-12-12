package com.vnua.controller;

import com.vnua.model.Publication;
import com.vnua.model.SysUser;
import com.vnua.service.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class DuyetController {

    @Autowired
    private PublicationService publicationService;

    @GetMapping("/duyet")
    public String duyetPage(Model model, HttpSession session) {
        SysUser user = (SysUser) session.getAttribute("loggedInUser");
        if (user == null || user.getUserType() != 99) {
            return "redirect:/login";
        }

        List<Publication> pending = publicationService.getPublicationsByStatus(0);
        model.addAttribute("pendingPublications", pending);
        return "duyet";
    }

    // Duyệt bài báo
    @PostMapping("/duyet/approve/{pubId}")
    public String approvePublication(
            @PathVariable int pubId,
            RedirectAttributes redirectAttributes,
            HttpSession session
    ) {
        SysUser user = (SysUser) session.getAttribute("loggedInUser");
        if (user == null || user.getUserType() != 99) {
            return "redirect:/login";
        }
        publicationService.updatePublicationStatus(pubId, 1); // 1 = đã duyệt
        redirectAttributes.addFlashAttribute("success", "✅ Đã duyệt bài báo ID: " + pubId);
        return "redirect:/duyet";
    }

    // Từ chối bài báo
    @PostMapping("/duyet/reject/{pubId}")
    public String rejectPublication(
            @PathVariable int pubId,
            @RequestParam String reason, // có thể thêm form nhập lý do
            RedirectAttributes redirectAttributes,
            HttpSession session
    ) {
        SysUser user = (SysUser) session.getAttribute("loggedInUser");
        if (user == null || user.getUserType() != 99) {
            return "redirect:/login";
        }
        publicationService.updatePublicationStatus(pubId, 2); // 2 = từ chối
        redirectAttributes.addFlashAttribute("success", "❌ Đã từ chối bài báo ID: " + pubId);
        return "redirect:/duyet";
    }
}