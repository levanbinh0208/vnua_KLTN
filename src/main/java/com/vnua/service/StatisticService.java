package com.vnua.service;

import com.vnua.mapper.StatisticMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.function.Supplier;
import java.util.*;

@Service
public class StatisticService {

    @Autowired
    private StatisticMapper statisticMapper;

    // Publication
    public int countPublications() { return safeCount(() -> statisticMapper.countPublications()); }
    public int countPendingPublications() { return safeCount(() -> statisticMapper.countPendingPublications()); }

    // Project
    public int countProjects() { return safeCount(() -> statisticMapper.countProjects()); }
    public int countPendingProjects() { return safeCount(() -> statisticMapper.countPendingProjects()); }

    // Conference
    public int countConferences() { return safeCount(() -> statisticMapper.countConferences()); }
    public int countPendingConferences() { return safeCount(() -> statisticMapper.countPendingConferences()); }

    // Book
    public int countBooks() { return safeCount(() -> statisticMapper.countBooks()); }
    public int countPendingBooks() { return safeCount(() -> statisticMapper.countPendingBooks()); }

    // Patent
    public int countPatents() { return safeCount(() -> statisticMapper.countPatents()); }
    public int countPendingPatents() { return safeCount(() -> statisticMapper.countPendingPatents()); }

    // Supervision
    public int countSupervisions() { return safeCount(() -> statisticMapper.countSupervisions()); }
    public int countPendingSupervisions() { return safeCount(() -> statisticMapper.countPendingSupervisions()); }

    // Thống kê
    public List<Map<String, Object>> getPublicationsByDept() { return safeList(() -> statisticMapper.getPublicationsByDept()); }
    public List<Map<String, Object>> getProjectsByDept() { return safeList(() -> statisticMapper.getProjectsByDept()); }
    public List<Map<String, Object>> getPublicationsByYear() { return safeList(() -> statisticMapper.getPublicationsByYear()); }

    // Helper
    private int safeCount(Supplier<Integer> supplier) {
        try { return supplier.get(); } catch (Exception e) { return 0; }
    }

    private <T> List<T> safeList(Supplier<List<T>> supplier) {
        try {
            List<T> list = supplier.get();
            return list != null ? list : Collections.emptyList();
        } catch (Exception e) { return Collections.emptyList(); }
    }

    public List<Map<String, Object>> getConferencesByDept() {
        try { return statisticMapper.getConferencesByDept(); }
        catch (Exception e) { return Collections.emptyList(); }
    }

    public List<Map<String, Object>> getBooksByDept() {
        try { return statisticMapper.getBooksByDept(); }
        catch (Exception e) { return Collections.emptyList(); }
    }

    public List<Map<String, Object>> getPatentsByDept() {
        try { return statisticMapper.getPatentsByDept(); }
        catch (Exception e) { return Collections.emptyList(); }
    }

    public List<Map<String, Object>> getSupervisionsByDept() {
        try { return statisticMapper.getSupervisionsByDept(); }
        catch (Exception e) { return Collections.emptyList(); }
    }
}