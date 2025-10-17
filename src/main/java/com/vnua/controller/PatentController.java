package com.vnua.controller;

import com.vnua.model.Patent;
import com.vnua.service.PatentService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/api/patent")
    @ResponseBody
    public ResponseEntity<?> insertPatent(@RequestBody Patent patent) {
        patentService.insertPatent(patent);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/patent/{id}")
    @ResponseBody
    public ResponseEntity<?> updatePatent(@PathVariable("id") int id, @RequestBody Patent patent) {
        patent.setPatentId(id);
        patentService.updatePatent(patent);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/patent/{id}")
    @ResponseBody
    public ResponseEntity<?> deletePatent(@PathVariable("id") int id) {
        patentService.deletePatent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/patent/{id}")
    @ResponseBody
    public ResponseEntity<?> getPatentById(@PathVariable("id") int id) {
        Patent patent = patentService.getPatentById(id);
        if (patent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(patent);
    }

}
