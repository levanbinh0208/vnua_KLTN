package com.vnua.controller;

import com.vnua.model.Project;
import com.vnua.service.ProjectService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class ProjectController {

    private final ProjectService projectService;

    private static final String UPLOAD_DIR = "uploads/projects/"; // Thư mục lưu file đề tài

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/project")
    @ResponseBody
    public List<Project> getProjects() {
        return projectService.getProjects();
    }

    @GetMapping("/project/{id}")
    @ResponseBody
    public ResponseEntity<?> getProjectById(@PathVariable("id") int id) {
        Project project = projectService.getProjectById(id);
        if (project == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(project);
    }

    @GetMapping("/project/{id}/file")
    public ResponseEntity<Resource> downloadFile(@PathVariable("id") int id) {
        Project project = projectService.getProjectById(id);
        if (project == null || project.getWordFileName() == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            Path filePath = Paths.get(UPLOAD_DIR).resolve(project.getWordFileName()).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                String contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
                if (project.getWordFileName().toLowerCase().endsWith(".doc")) {
                    contentType = "application/msword";
                }

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION,
                                "attachment; filename=\"" + StringUtils.cleanPath(project.getWordFileName()) + "\"")
                        .header(HttpHeaders.CONTENT_TYPE, contentType)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/project")
    @ResponseBody
    public ResponseEntity<?> insertProject(
            @RequestPart("name") String name,
            @RequestPart(value = "deptId", required = false) Integer deptId,
            @RequestPart(value = "startDate", required = false) String startDate,
            @RequestPart(value = "endDate", required = false) String endDate,
            @RequestPart(value = "wordFile", required = false) MultipartFile wordFile
    ) {
        Project project = new Project();
        project.setName(name);
        project.setDeptId(deptId);
        project.setStartDate(startDate);
        project.setEndDate(endDate);

        if (wordFile != null && !wordFile.isEmpty()) {
            String originalName = wordFile.getOriginalFilename();
            if (originalName == null || originalName.isEmpty()) {
                originalName = "unknown.docx";
            }
            originalName = StringUtils.cleanPath(originalName);
            originalName = originalName.replaceAll("[^\\w.\\-\\u4e00-\\u9fff\\u00C0-\\u017F]", "_");

            if (!originalName.toLowerCase().endsWith(".doc") && !originalName.toLowerCase().endsWith(".docx")) {
                return ResponseEntity.badRequest().body("Chỉ hỗ trợ file .doc hoặc .docx");
            }

            String baseName = originalName.substring(0, originalName.lastIndexOf('.'));
            String ext = originalName.substring(originalName.lastIndexOf('.'));
            String safeName = System.currentTimeMillis() + "_" + baseName;
            if (safeName.length() > 80) {
                safeName = System.currentTimeMillis() + "_" + baseName.substring(0, Math.min(30, baseName.length()));
            }
            safeName += ext;

            Path uploadPath = Paths.get(UPLOAD_DIR);
            try {
                Files.createDirectories(uploadPath);
                Path filePath = uploadPath.resolve(safeName);
                Files.copy(wordFile.getInputStream(), filePath);
                project.setWordFileName(safeName);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Lỗi khi lưu file: " + e.getMessage());
            }
        }

        projectService.insertProject(project);
        return ResponseEntity.ok(project);
    }

    @PutMapping("/project/{id}")
    @ResponseBody
    public ResponseEntity<?> updateProject(
            @PathVariable("id") int id,
            @RequestPart("name") String name,
            @RequestPart(value = "deptId", required = false) Integer deptId,
            @RequestPart(value = "startDate", required = false) String startDate,
            @RequestPart(value = "endDate", required = false) String endDate,
            @RequestPart(value = "wordFile", required = false) MultipartFile wordFile,
            @RequestPart(value = "wordFileName", required = false) String currentFileName
    ) {
        Project existing = projectService.getProjectById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        existing.setName(name);
        existing.setDeptId(deptId);
        existing.setStartDate(startDate);
        existing.setEndDate(endDate);

        if (wordFile != null && !wordFile.isEmpty()) {
            String originalName = wordFile.getOriginalFilename();
            if (originalName == null || originalName.isEmpty()) {
                originalName = "unknown.docx";
            }
            originalName = StringUtils.cleanPath(originalName);
            originalName = originalName.replaceAll("[^\\w.\\-\\u4e00-\\u9fff\\u00C0-\\u017F]", "_");

            if (!originalName.toLowerCase().endsWith(".doc") && !originalName.toLowerCase().endsWith(".docx")) {
                return ResponseEntity.badRequest().body("Chỉ hỗ trợ file .doc hoặc .docx");
            }

            if (existing.getWordFileName() != null) {
                try {
                    Path oldPath = Paths.get(UPLOAD_DIR).resolve(existing.getWordFileName());
                    Files.deleteIfExists(oldPath);
                } catch (IOException ignored) {}
            }

            String baseName = originalName.substring(0, originalName.lastIndexOf('.'));
            String ext = originalName.substring(originalName.lastIndexOf('.'));
            String safeName = System.currentTimeMillis() + "_" + baseName;
            if (safeName.length() > 80) {
                safeName = System.currentTimeMillis() + "_" + baseName.substring(0, Math.min(30, baseName.length()));
            }
            safeName += ext;

            Path uploadPath = Paths.get(UPLOAD_DIR);
            try {
                Files.createDirectories(uploadPath);
                Path filePath = uploadPath.resolve(safeName);
                Files.copy(wordFile.getInputStream(), filePath);
                existing.setWordFileName(safeName);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Lỗi khi lưu file: " + e.getMessage());
            }
        } else {
            existing.setWordFileName(currentFileName);
        }

        projectService.updateProject(existing);
        return ResponseEntity.ok(existing);
    }

    @DeleteMapping("/project/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteProject(@PathVariable("id") int id) {
        Project existing = projectService.getProjectById(id);
        if (existing != null && existing.getWordFileName() != null) {
            try {
                Path filePath = Paths.get(UPLOAD_DIR).resolve(existing.getWordFileName());
                Files.deleteIfExists(filePath);
            } catch (IOException ignored) {}
        }
        projectService.deleteProject(id);
        return ResponseEntity.ok().build();
    }
}