package com.vnua.mapper;

import com.vnua.model.Project;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjectMapper {
    List<Project> getAllProjects();

    Project getProjectById(int id);

    void insertProject(Project project);

    void updateProject(Project project);

    void deleteProject(int id);

    List<Project> getByStatus(int status);

    void updateStatus(@Param("id") int id,
                      @Param("status") int status);
}
