package com.vnua.controller;

import com.vnua.model.Patent;
import com.vnua.service.PatentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PatentController {

    private final PatentService patentService;

    public PatentController(PatentService patentService) {
        this.patentService = patentService;
    }

    @GetMapping("/patent")
    public String patentPage() {
        return "indexUser";
    }

    @GetMapping("/api/patent")
    @ResponseBody
    public List<Patent> getPatents() {
        return patentService.getPatents();
    }
}
