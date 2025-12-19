package com.vnua.mapper;

import com.vnua.model.Project;
<<<<<<< HEAD
import org.apache.ibatis.annotations.Param;
=======
>>>>>>> 459e049d54c61ab01b731c30e82a712e98f6abf9

import java.util.List;

public interface ProjectMapper {
    List<Project> getAllProjects();

    Project getProjectById(int id);

    void insertProject(Project project);

    void updateProject(Project project);

    void deleteProject(int id);

    List<Project> getByStatus(int status);

<<<<<<< HEAD
    void updateStatus(@Param("id") int id,
                      @Param("status") int status);

=======
    void updateStatus(int id, int status);
>>>>>>> 459e049d54c61ab01b731c30e82a712e98f6abf9
}
