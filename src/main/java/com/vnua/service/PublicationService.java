package com.vnua.service;

import com.vnua.mapper.PublicationMapper;
import com.vnua.model.Publication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicationService {
    private final PublicationMapper publicationMapper;

    public PublicationService(PublicationMapper publicationMapper) {
        this.publicationMapper = publicationMapper;
    }

    public List<Publication> getAllPublications() {
        return publicationMapper.getAllPublications();
    }
}