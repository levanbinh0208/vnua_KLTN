package com.vnua.controller;

import com.vnua.model.Dept;
import com.vnua.model.SysUser;
import com.vnua.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class PhongBanController {

    @Autowired
    private DeptService deptService;

    @GetMapping("/phongban")
    public String listDepts(Model model, HttpSession session) {
        SysUser user = (SysUser) session.getAttribute("loggedInUser");
        if (user == null || user.getUserType() != 99) {
            return "redirect:/login";
        }
        List<Dept> depts = deptService.getAllDepts();
        model.addAttribute("depts", depts);
        return "phongban"; // → phongban.html
    }

    @PostMapping("/phongban/add")
    public String addDept(
            @RequestParam String deptName,
            RedirectAttributes redirectAttributes,
            HttpSession session
    ) {
        SysUser user = (SysUser) session.getAttribute("loggedInUser");
        if (user == null || user.getUserType() != 99) {
            return "redirect:/login";
        }
        deptService.insertDept(deptName);
        redirectAttributes.addFlashAttribute("success", "✅ Thêm phòng ban thành công!");
        return "redirect:/phongban";
    }

    @PostMapping("/phongban/update")
    public String updateDept(
            @RequestParam int deptId,
            @RequestParam String deptName,
            RedirectAttributes redirectAttributes,
            HttpSession session
    ) {
        SysUser user = (SysUser) session.getAttribute("loggedInUser");
        if (user == null || user.getUserType() != 99) {
            return "redirect:/login";
        }
        deptService.updateDept(deptId, deptName);
        redirectAttributes.addFlashAttribute("success", "✅ Cập nhật phòng ban thành công!");
        return "redirect:/phongban";
    }

    @GetMapping("/phongban/delete/{deptId}")
    public String deleteDept(
            @PathVariable int deptId,
            RedirectAttributes redirectAttributes,
            HttpSession session
    ) {
        SysUser user = (SysUser) session.getAttribute("loggedInUser");
        if (user == null || user.getUserType() != 99) {
            return "redirect:/login";
        }
        try {
            deptService.deleteDept(deptId);
            redirectAttributes.addFlashAttribute("success", "✅ Xóa phòng ban thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "❌ Không thể xóa (phòng ban đang có giảng viên).");
        }
        return "redirect:/phongban";
    }
}