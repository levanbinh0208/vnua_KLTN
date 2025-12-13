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

        model.addAttribute("totalPublications", statisticService.countPublications());
        model.addAttribute("totalProjects", statisticService.countProjects());
        model.addAttribute("totalConferences", statisticService.countConferences());
        model.addAttribute("totalBooks", statisticService.countBooks());
        model.addAttribute("totalPatents", statisticService.countPatents());
        model.addAttribute("totalSupervisions", statisticService.countSupervisions());

        model.addAttribute("pendingPublications", statisticService.countPendingPublications());
        model.addAttribute("pendingProjects", statisticService.countPendingProjects());
        model.addAttribute("pendingConferences", statisticService.countPendingConferences());
        model.addAttribute("pendingBooks", statisticService.countPendingBooks());
        model.addAttribute("pendingPatents", statisticService.countPendingPatents());
        model.addAttribute("pendingSupervisions", statisticService.countPendingSupervisions());

        model.addAttribute("publicationsByDept", statisticService.getPublicationsByDept());
        model.addAttribute("projectsByDept", statisticService.getProjectsByDept());
        model.addAttribute("conferencesByDept", statisticService.getConferencesByDept());
        model.addAttribute("booksByDept", statisticService.getBooksByDept());
        model.addAttribute("patentsByDept", statisticService.getPatentsByDept());
        model.addAttribute("supervisionsByDept", statisticService.getSupervisionsByDept());

        return "baocao";
    }
}