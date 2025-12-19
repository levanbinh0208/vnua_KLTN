package com.vnua.controller;

import com.vnua.model.Notification;
import com.vnua.model.SysUser;
import com.vnua.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<?> getNotifications(HttpSession session) {
        SysUser user = (SysUser) session.getAttribute("loggedInUser");
        if (user == null) {
            return ResponseEntity.status(401).body("Chưa đăng nhập");
        }
        List<Notification> notifications = notificationService.getNotificationsByUserId(user.getUser_id());
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/unread")
    @ResponseBody
    public ResponseEntity<?> getUnreadNotifications(HttpSession session) {
        SysUser user = (SysUser) session.getAttribute("loggedInUser");
        if (user == null) {
            return ResponseEntity.status(401).body("Chưa đăng nhập");
        }
        List<Notification> notifications = notificationService.getUnreadNotifications(user.getUser_id());
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/unread/count")
    @ResponseBody
    public ResponseEntity<?> countUnreadNotifications(HttpSession session) {
        SysUser user = (SysUser) session.getAttribute("loggedInUser");
        if (user == null) {
            return ResponseEntity.status(401).body("Chưa đăng nhập");
        }
        int count = notificationService.countUnreadNotifications(user.getUser_id());
        Map<String, Integer> response = new HashMap<>();
        response.put("count", count);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/read")
    @ResponseBody
    public ResponseEntity<?> markAsRead(@PathVariable("id") Integer notificationId) {
        notificationService.markAsRead(notificationId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Đã đánh dấu đã đọc");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/read-all")
    @ResponseBody
    public ResponseEntity<?> markAllAsRead(HttpSession session) {
        SysUser user = (SysUser) session.getAttribute("loggedInUser");
        if (user == null) {
            return ResponseEntity.status(401).body("Chưa đăng nhập");
        }
        notificationService.markAllAsRead(user.getUser_id());
        Map<String, String> response = new HashMap<>();
        response.put("message", "Đã đánh dấu tất cả đã đọc");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteNotification(@PathVariable("id") Integer notificationId) {
        notificationService.deleteNotification(notificationId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Đã xóa thông báo");
        return ResponseEntity.ok(response);
    }
}