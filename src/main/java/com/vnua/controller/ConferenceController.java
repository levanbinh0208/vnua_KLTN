package com.vnua.controller;

import com.vnua.model.Conference;
import com.vnua.service.ConferenceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ConferenceController {
    private final ConferenceService conferenceService;

    public ConferenceController(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

    @GetMapping("/conference")
    @ResponseBody
    public List<Conference> getConferences() {
        return conferenceService.getConferences();
    }
}