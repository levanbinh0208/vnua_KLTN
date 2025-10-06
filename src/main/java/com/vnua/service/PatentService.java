package com.vnua.service;

import com.vnua.mapper.PatentMapper;
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
}

