package com.vnua.controller;

import com.vnua.model.Patent;
import com.vnua.service.PatentService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PatentController {

    private final PatentService patentService;

    public PatentController(PatentService patentService) {
        this.patentService = patentService;
    }

    @GetMapping("/patent")
    @ResponseBody
    public List<Patent> getPatents() {
        return patentService.getPatents();
    }

    @PostMapping("/patent")
    @ResponseBody
    public int insertPatent(@RequestBody Patent patent, Model model) {
        return patentService.insertPatent(patent);
    }

    @PutMapping("/patent/{id}")
    @ResponseBody
    public ResponseEntity<?> updatePatent(@PathVariable("id") int id, @RequestBody Patent patent) {
        patent.setPatentId(id);
        patentService.updatePatent(patent);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/patent/{id}")
    @ResponseBody
    public ResponseEntity<?> deletePatent(@PathVariable("id") int id) {
        patentService.deletePatent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/patent/{id}")
    @ResponseBody
    public ResponseEntity<?> getPatentById(@PathVariable("id") int id) {
        Patent patent = patentService.getPatentById(id);
        if (patent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(patent);
    }

}
