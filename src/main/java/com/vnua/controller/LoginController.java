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
        String login_name = user.getLogin_name();
        String password = user.getPassword();
        user = loginMapper.findByUsernameAndPassword(login_name, password);
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Sai tài khoản hoặc mật khẩu");
            return "redirect:/login";
        }
        session.setAttribute("loggedInUser", user);
        loginMapper.updateLoginDate(user.getUser_id());
        if(user.getUserType() == 99)
            return "redirect:index";
        else
            return "redirect:indexUser";
    }

    @GetMapping("/index")
    public String showIndex(HttpSession session, Model model) {
        SysUser user = (SysUser) session.getAttribute("loggedInUser");

        if (user != null) {
            loginMapper.logFullName(user.getLogin_name());
            model.addAttribute("fullname", user.getUsername());
        } else {
            return "redirect:/login";
        }

        return "index";
    }

    @GetMapping("/indexUser")
    public String showIndexUser(HttpSession session, Model model) {
        SysUser user = (SysUser) session.getAttribute("loggedInUser");

        if (user != null) {
            loginMapper.logFullName(user.getLogin_name());
            model.addAttribute("fullname", user.getUsername());
        } else {
            return "redirect:/login";
        }

        return "indexUser";
    }

<<<<<<< HEAD
    @GetMapping("/index/profile")
    public String showProfile(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String delFlag,
            Model model,
            HttpSession session) {

        SysUser user = (SysUser) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }

        String email = user.getEmail();

        List<SysUser> users = loginService.showProfile(name, role, delFlag, email);
        model.addAttribute("users", users);

        return "profile";
    }


=======
    @GetMapping("index/profile")
    public String showProfile(@RequestParam(required = false) String name,
                              @RequestParam(required = false) String role,
                              @RequestParam(required = false) String delFlag,
                              Model model,HttpSession session) {
        SysUser user = (SysUser) session.getAttribute("loggedInUser");
        if (user != null) {
            List<SysUser> users = loginService.showProfile(name, role, delFlag);
            model.addAttribute("users", users);
        } else {
            return "redirect:/login";
        }
        return "profile";
    }

>>>>>>> 459e049d54c61ab01b731c30e82a712e98f6abf9
    @PostMapping("index/profile/edit")
    public String updateProfile(
            @ModelAttribute SysUser user,
            RedirectAttributes redirectAttributes,
            HttpSession session
    ) {
        SysUser currentUser = (SysUser) session.getAttribute("loggedInUser");
        if (currentUser == null || currentUser.getUserType() != 99) {
            return "redirect:/login";
        }
        try {
            loginService.updateProfile(user);
            redirectAttributes.addFlashAttribute("success", "✅ Cập nhật thông tin thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "❌ Lỗi khi cập nhật: " + e.getMessage());
        }
        return "redirect:/index/profile";
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

    @GetMapping("/index/profile/add")
    public String showAddForm(Model model, HttpSession session) {
        SysUser currentUser = (SysUser) session.getAttribute("loggedInUser");
        if (currentUser == null || currentUser.getUserType() != 99) {
            return "redirect:/login";
        }
        model.addAttribute("user", new SysUser());
        return "addUser"; // → addUser.html
    }

    @PostMapping("/index/profile/add")
    public String addUser(
            @ModelAttribute SysUser user,
            RedirectAttributes redirectAttributes,
            HttpSession session
    ) {
        SysUser currentUser = (SysUser) session.getAttribute("loggedInUser");
        if (currentUser == null || currentUser.getUserType() != 99) {
            return "redirect:/login";
        }
        try {
            loginService.insertUser(user);
            redirectAttributes.addFlashAttribute("success", "✅ Thêm giảng viên thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "❌ Lỗi khi thêm: " + e.getMessage());
        }
        return "redirect:/index/profile";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

}
