package com.vnua.service;

import com.vnua.mapper.PatentMapper;
import com.vnua.model.Book;
import com.vnua.model.Patent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatentService {
    private final PatentMapper patentMapper;

    public PatentService(PatentMapper patentMapper) {
        this.patentMapper = patentMapper;
    }

    public List<Patent> getPatents() {
        return patentMapper.getPatents();
    }

    public int insertPatent(Patent patent) {
        return patentMapper.insertPatent(patent);
    }

    public void updatePatent(Patent patent) {
        patentMapper.updatePatent(patent);
    }

    public void deletePatent(int patentId) {
        patentMapper.deletePatent(patentId);
    }

    public Patent getPatentById(int id) {
        return patentMapper.findById(id);
    }

    public List<Patent> getByStatus(int status) {
        return patentMapper.getByStatus(status);
    }

    public void updateStatus(int id, int status) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID không hợp lệ: " + id);
        }
        patentMapper.updateStatus(id, status);
    }
}
