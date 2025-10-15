package com.vnua.service;

import com.vnua.mapper.ConferenceMapper;
import com.vnua.model.Conference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConferenceService {
    private final ConferenceMapper conferenceMapper;

    public ConferenceService(ConferenceMapper conferenceMapper) {
        this.conferenceMapper = conferenceMapper;
    }

    public List<Conference> getConferences() {
        return conferenceMapper.getConferences();
    }
}