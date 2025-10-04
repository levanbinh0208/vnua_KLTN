package com.vnua.controller;

import com.vnua.model.Project;
import com.vnua.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/project")
    public String projectPage() {
        return "indexUser";
    }

    @GetMapping("/api/project")
    @ResponseBody
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }
}