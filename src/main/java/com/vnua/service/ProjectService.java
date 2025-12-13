package com.vnua.service;

import com.vnua.mapper.ProjectMapper;
import com.vnua.model.Project;
import com.vnua.model.Publication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    private final ProjectMapper projectMapper;

    public ProjectService(ProjectMapper projectMapper) {
        this.projectMapper = projectMapper;
    }

    public List<Project> getProjects() {
        return projectMapper.getAllProjects();
    }

    public void insertProject(Project project) {
        projectMapper.insertProject(project);
    }

    public Project getProjectById(int id) {
        return projectMapper.getProjectById(id);
    }

    public void updateProject(Project project) {
        projectMapper.updateProject(project);
    }

    public void deleteProject(int id) {
        projectMapper.deleteProject(id);
    }

    public List<Project> getByStatus(int status) {
        return projectMapper.getByStatus(status);
    }

    public void updateStatus(int id, int status) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID không hợp lệ: " + id);
        }
        projectMapper.updateStatus(id, status);
    }
}