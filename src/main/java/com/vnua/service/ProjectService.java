package com.vnua.service;

import com.vnua.mapper.ProjectMapper;
import com.vnua.model.Project;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    private final ProjectMapper projectMapper;

    public ProjectService(ProjectMapper projectMapper) {
        this.projectMapper = projectMapper;
    }

    public List<Project> getAllProjects() {
        return projectMapper.getAllProjects();
    }
}