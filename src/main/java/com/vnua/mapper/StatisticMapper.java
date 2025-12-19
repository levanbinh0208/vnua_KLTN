package com.vnua.mapper;

import java.util.List;
import java.util.Map;

public interface StatisticMapper {
    // Publication
    int countPublications();
    int countPendingPublications();

    // Project
    int countProjects();
    int countPendingProjects();

    // Conference
    int countConferences();
    int countPendingConferences();

    // Book
    int countBooks();
    int countPendingBooks();

    // Patent
    int countPatents();
    int countPendingPatents();

    // Supervision
    int countSupervisions();
    int countPendingSupervisions();

    // Thống kê
    List<Map<String, Object>> getPublicationsByDept();
    List<Map<String, Object>> getProjectsByDept();
    List<Map<String, Object>> getPublicationsByYear();

    List<Map<String, Object>> getConferencesByDept();
    List<Map<String, Object>> getBooksByDept();
    List<Map<String, Object>> getPatentsByDept();
    List<Map<String, Object>> getSupervisionsByDept();
}