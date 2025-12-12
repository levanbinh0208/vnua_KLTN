package com.vnua.controller;

import com.vnua.model.SysUser;
import com.vnua.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaocaoController {

    @Autowired
    private StatisticService statisticService;

    @GetMapping("/baocao")
    public String baocaoPage(Model model, HttpSession session) {
        SysUser user = (SysUser) session.getAttribute("loggedInUser");
        if (user == null || user.getUserType() != 99) {
            return "redirect:/login";
        }

        model.addAttribute("totalUsers", statisticService.countUsers());
        model.addAttribute("totalPublications", statisticService.countPublications());
        model.addAttribute("pendingPublications", statisticService.countPendingPublications());
        model.addAttribute("publicationsByDept", statisticService.getPublicationsByDept());
        model.addAttribute("publicationsByYear", statisticService.getPublicationsByYear());

        return "baocao";
    }
}