package com.vnua.mapper;

import java.util.Map;

public interface StatisticMapper {

    int countUsers();
    int countPublications();
    int countPendingPublications();
    Map<String, Integer> getPublicationsByDept();
    Map<String, Integer> getPublicationsByYear();
}