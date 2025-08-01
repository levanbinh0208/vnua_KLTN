package com.vnua.controller;

import com.vnua.mapper.LoginMapper;
import com.vnua.model.SysUser;
import com.vnua.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private LoginMapper loginMapper;
    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/doLogin")
    public String processLogin(@RequestParam String loginname,
                               @RequestParam String password,
                               Model model ,HttpSession session) {
        SysUser user = loginMapper.findByUsernameAndPassword(loginname, password);
        if (user != null) {
            int i = loginMapper.updateLoginDate(loginname);
            session.setAttribute("loggedInUser", user);
            return "redirect:/index";
        } else {
            model.addAttribute("error", "Sai tài khoản hoặc mật khẩu");
            return "login";
        }
    }

    @GetMapping("/index")
    public String showIndex(HttpSession session, Model model) {
        SysUser user = (SysUser) session.getAttribute("loggedInUser");

        if (user != null) {
            model.addAttribute("username", user.getLoginname());
            user = loginMapper.logFullName(user.getLoginname());
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

}
