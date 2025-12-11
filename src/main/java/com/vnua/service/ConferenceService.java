package com.vnua.service;

import com.vnua.mapper.ConferenceMapper;
import com.vnua.mapper.PublicationMapper;
import com.vnua.model.Conference;
import com.vnua.model.Publication;
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

    public Conference getConferenceById(int id) {
        return conferenceMapper.findById(id);
    }

    public void insertConference(Conference conf) {
        conferenceMapper.insertConference(conf);
    }

    public void updateConference(Conference conf) {
        conferenceMapper.updateConference(conf);
    }

    public void deleteConference(int id) {
        conferenceMapper.deleteConference(id);
    }

}