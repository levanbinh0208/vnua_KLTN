package com.vnua.controller;

import com.vnua.model.Supervision;
import com.vnua.service.SupervisionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SupervisionController {
    private final SupervisionService supervisionService;

    public SupervisionController(SupervisionService supervisionService) {
        this.supervisionService = supervisionService;
    }

    @GetMapping("/supervision")
    @ResponseBody
    public List<Supervision> getSupervisions() {
        return supervisionService.getSupervisions();
    }
}