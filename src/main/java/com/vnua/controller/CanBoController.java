package com.vnua.controller;

import com.vnua.model.CanBo;
import com.vnua.service.CanBoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/canbo")
public class CanBoController {
    @Autowired
    private CanBoService canBoService;

    @GetMapping
    public String listCanBo(Model model) {
        model.addAttribute("canBoList", canBoService.findAll());
        return "canbo/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("canBo", new CanBo());
        return "canbo/add";
    }

    @PostMapping("/add")
    public String addCanBo(@ModelAttribute CanBo canBo) {
        canBoService.save(canBo);
        return "redirect:/canbo";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        CanBo canBo = canBoService.findById(id);
        model.addAttribute("canBo", canBo);
        return "canbo/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateCanBo(@PathVariable Long id, @ModelAttribute CanBo canBo) {
        canBo.setId(id);
        canBoService.save(canBo);
        return "redirect:/canbo";
    }

    @GetMapping("/delete/{id}")
    public String deleteCanBo(@PathVariable Long id) {
        canBoService.deleteById(id);
        return "redirect:/canbo";
    }
}