// src/main/java/com/vnua/service/StatisticService.java
package com.vnua.service;

import com.vnua.mapper.StatisticMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class StatisticService {

    @Autowired
    private StatisticMapper statisticMapper;

    public int countUsers() {
        return statisticMapper.countUsers();
    }

    public int countPublications() {
        return statisticMapper.countPublications();
    }

    public int countPendingPublications() {
        return statisticMapper.countPendingPublications();
    }

    public Map<String, Integer> getPublicationsByDept() {
        return statisticMapper.getPublicationsByDept();
    }

    public Map<String, Integer> getPublicationsByYear() {
        return statisticMapper.getPublicationsByYear();
    }
}