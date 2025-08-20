package com.vnua.controller;

import com.vnua.mapper.LoginMapper;
import com.vnua.model.SysUser;
import com.vnua.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private LoginMapper loginMapper;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(SysUser user, RedirectAttributes redirectAttributes,
                               Model model ,HttpSession session) {
        String loginname = user.getLoginname();
        String password = user.getPassword();
        user = loginMapper.findByUsernameAndPassword(loginname, password);
        if (user != null) {
            loginMapper.updateLoginDate(user.getUser_id());
            model.addAttribute("username", loginname);
            session.setAttribute("loggedInUser", user);
        } else {
            redirectAttributes.addFlashAttribute("error", "Sai tài khoản hoặc mật khẩu");
            return "redirect:/login";
        }
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String showIndex(HttpSession session, Model model) {
        SysUser user = (SysUser) session.getAttribute("loggedInUser");

        if (user != null) {
            model.addAttribute("username", user.getLoginname());
            user = loginService.logFullName(user.getLoginname());
        } else {
            return "redirect:/login";
        }

        return "index";
    }

    @GetMapping("index/profile")
    public String showProfile(@RequestParam(required = false) String name,
                              @RequestParam(required = false) String dept,
                              @RequestParam(required = false) String role,
                              Model model,HttpSession session) {
        SysUser user = (SysUser) session.getAttribute("loggedInUser");
        if (user != null) {
            List<SysUser> users = loginService.showProfile(name, dept, role);
            model.addAttribute("users", users);
        } else {
            return "redirect:/login";
        }
        return "profile";
    }

    @GetMapping("/index/profile/del/{user_id}")
    public String del(@PathVariable("user_id") int user_id,
                      RedirectAttributes redirectAttributes,
                      HttpSession session) {
        SysUser currentUser = (SysUser) session.getAttribute("loggedInUser");
        if (currentUser == null) {
            return "redirect:/login";
        }

        int result = loginService.del(user_id);
        if (result == 0) {
            redirectAttributes.addFlashAttribute("error", "❌ Xóa người dùng thất bại!");
        } else {
            redirectAttributes.addFlashAttribute("success", "✅ Xóa người dùng thành công!");
        }

        return "redirect:/index/profile";
    }


//    @GetMapping("index/profile/edit")
//    public String showEditProfile(@RequestParam String loginname, Model model,HttpSession session) {
//        SysUser user = (SysUser) session.getAttribute("loggedInUser");
//        if (user != null) {
//            SysUser user1 = loginService.findUserByLoginname(loginname);
//            model.addAttribute("user", user1);
//        } else {
//            return "redirect:/login";
//        }
//        return "editProfile";
//    }
//
//    @PostMapping("index/profile/edit")
//    public String updateProfile(@ModelAttribute SysUser user, Model model,HttpSession session) {
//        SysUser user1 = (SysUser) session.getAttribute("loggedInUser");
//        if (user1 != null) {
//            loginService.updateProfile(user);
//        } else {
//            return "redirect:/login";
//        }
//        return "redirect:/index/profile";
//    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

}
