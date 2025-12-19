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
    @Autowired private NotificationService notificationService;

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

        // Lấy userId và tên item trước khi duyệt
        Integer userId = getUserIdFromItem(type, id);
        String itemTitle = getItemTitle(type, id);

        // Duyệt item
        approveItem(type, id, 1);

        // Tạo thông báo cho giảng viên
        if (userId != null) {
            Notification notification = new Notification();
            notification.setUserId(userId);
            notification.setType(type);
            notification.setRefId(id);
            notification.setTitle(getTypeName(type) + " đã được duyệt");
            notification.setMessage(getTypeName(type) + " \"" + itemTitle + "\" của bạn đã được phê duyệt bởi quản trị viên.");
            notificationService.createNotification(notification);
        }

        redirectAttributes.addFlashAttribute("success", "✅ Đã duyệt " + getTypeName(type) + " ID: " + id);
        return "redirect:/duyet";
    }

    @PostMapping("/duyet/reject/{type}/{id}")
    public String reject(
            @PathVariable String type,
            @PathVariable int id,
            @RequestParam(required = false) String reason,
            RedirectAttributes redirectAttributes,
            HttpSession session
    ) {
        checkPermission(session);

        // Lấy userId và tên item trước khi từ chối
        Integer userId = getUserIdFromItem(type, id);
        String itemTitle = getItemTitle(type, id);

        // Từ chối item
        approveItem(type, id, 2);

        // Tạo thông báo từ chối
        if (userId != null) {
            Notification notification = new Notification();
            notification.setUserId(userId);
            notification.setType(type);
            notification.setRefId(id);
            notification.setTitle(getTypeName(type) + " chưa được phê duyệt");
            notification.setMessage(getTypeName(type) + " \"" + itemTitle + "\" chưa được phê duyệt. " +
                    "Lý do: " + (reason != null && !reason.isEmpty() ? reason : "Không có lý do cụ thể"));
            notificationService.createNotification(notification);
        }

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

    private Integer getUserIdFromItem(String type, int id) {
        switch (type) {
            case "publication":
                Publication pub = publicationService.getPublicationById(id);
                return pub != null ? pub.getUserId() : null;
            case "project":
                Project proj = projectService.getProjectById(id);
                return proj != null ? proj.getUserId() : null;
            case "conference":
                Conference conf = conferenceService.getConferenceById(id);
                return conf != null ? conf.getUserId() : null;
            case "book":
                Book book = bookService.getBookById(id);
                return book != null ? book.getUserId() : null;
            case "patent":
                Patent patent = patentService.getPatentById(id);
                return patent != null ? patent.getUserId() : null;
            case "supervision":
                Supervision sup = supervisionService.getSupervisionById(id);
                return sup != null ? sup.getUserId() : null;
            default: return null;
        }
    }

    private String getItemTitle(String type, int id) {
        switch (type) {
            case "publication":
                Publication pub = publicationService.getPublicationById(id);
                return pub != null ? pub.getTitle() : "";
            case "project":
                Project proj = projectService.getProjectById(id);
                return proj != null ? proj.getName() : "";
            case "conference":
                Conference conf = conferenceService.getConferenceById(id);
                return conf != null ? conf.getTitle() : "";
            case "book":
                Book book = bookService.getBookById(id);
                return book != null ? book.getTitle() : "";
            case "patent":
                Patent patent = patentService.getPatentById(id);
                return patent != null ? patent.getTitle() : "";
            case "supervision":
                Supervision sup = supervisionService.getSupervisionById(id);
                return sup != null ? sup.getStudentName() : "";
            default: return "";
        }
    }

    private String getTypeName(String type) {
        return switch (type) {
            case "publication" -> "Bài báo";
            case "project" -> "Đề tài";
            case "conference" -> "Hội thảo";
            case "book" -> "Sách";
            case "patent" -> "Bằng sáng chế";
            case "supervision" -> "Hướng dẫn";
            default -> type;
        };
    }
}