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

    public Publication getPublicationById(int id) {
        return publicationMapper.findById(id);
    }

    public void insertPublication(Publication publication) {
        publicationMapper.insertPublication(publication);
    }

    public void updatePublication(Publication publication) {
        publicationMapper.updatePublication(publication);
    }

    public void deletePublication(int id) {
        publicationMapper.deletePublication(id);
    }

    public List<String> getAuthors() {
        return publicationMapper.getAuthors();
    }

    public void updatePublicationStatus(int pubId, int status) {
        if (pubId <= 0) {
            throw new IllegalArgumentException("ID bài báo không hợp lệ: " + pubId);
        }
        publicationMapper.updateStatus(pubId, status);
    }

    public List<Publication> getPublicationsByStatus(int status) {
        return publicationMapper.findByStatus(status);
    }
}
