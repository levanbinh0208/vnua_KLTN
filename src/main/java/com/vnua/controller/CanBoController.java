package com.vnua.controller;

import com.vnua.model.CanBo;
import com.vnua.service.CanBoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CanBoController {

    @Autowired
    private CanBoService canBoService;

    @GetMapping("/canbo")
    public String list(Model model) {
        List<CanBo> list = canBoService.getAllCanBo();
        model.addAttribute("list", list);
        return "canbo/list";
    }
}
