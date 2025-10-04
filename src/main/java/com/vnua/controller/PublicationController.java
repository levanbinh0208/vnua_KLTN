package com.vnua.controller;

import com.vnua.model.Publication;
import com.vnua.service.PublicationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;


@Controller
public class PublicationController {

    private final PublicationService publicationService;

    public PublicationController(PublicationService publicationService) {this.publicationService = publicationService;}

    @GetMapping("/publication")
    public String publicationPage() {
        return "indexUser";
    }

    @GetMapping("/api/publication")
    @ResponseBody
    public List<Publication> getAllPublications() {
        return publicationService.getAllPublications();
    }
}