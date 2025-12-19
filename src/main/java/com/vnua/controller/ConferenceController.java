package com.vnua.controller;

import com.vnua.model.Conference;
<<<<<<< HEAD
import com.vnua.model.Publication;
import com.vnua.model.SysUser;
import com.vnua.service.ConferenceService;
import jakarta.servlet.http.HttpSession;
=======
import com.vnua.service.ConferenceService;
>>>>>>> 459e049d54c61ab01b731c30e82a712e98f6abf9
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
public class ConferenceController {

    private final ConferenceService conferenceService;

    private static final String UPLOAD_DIR = "uploads/conferences/";

    public ConferenceController(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

    @GetMapping("/conference")
    @ResponseBody
    public List<Conference> getConferences() {
        return conferenceService.getConferences();
    }

    @GetMapping("/conference/{id}")
    @ResponseBody
    public ResponseEntity<?> getConferenceById(@PathVariable("id") int id) {
        Conference conf = conferenceService.getConferenceById(id);
        if (conf == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(conf);
    }

    @GetMapping("/conference/{id}/file")
    public ResponseEntity<Resource> downloadFile(@PathVariable("id") int id) {
        Conference conf = conferenceService.getConferenceById(id);
        if (conf == null || conf.getWordFileName() == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            Path filePath = Paths.get(UPLOAD_DIR).resolve(conf.getWordFileName()).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                String contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
                if (conf.getWordFileName().toLowerCase().endsWith(".doc")) {
                    contentType = "application/msword";
                }

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION,
                                "attachment; filename=\"" + StringUtils.cleanPath(conf.getWordFileName()) + "\"")
                        .header(HttpHeaders.CONTENT_TYPE, contentType)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/conference")
    @ResponseBody
    public ResponseEntity<?> insertConference(
            @RequestPart("title") String title,
            @RequestPart(value = "location", required = false) String location,
            @RequestPart(value = "date", required = false) String date,
            @RequestPart(value = "deptId", required = false) Integer deptId,
<<<<<<< HEAD
            @RequestPart(value = "role", required = false) String role,
            @RequestPart(value = "wordFile", required = false) MultipartFile wordFile
            , HttpSession session
    ) {
        SysUser user = (SysUser) session.getAttribute("loggedInUser");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
=======
            @RequestPart(value = "wordFile", required = false) MultipartFile wordFile
    ) {
>>>>>>> 459e049d54c61ab01b731c30e82a712e98f6abf9
        Conference conf = new Conference();
        conf.setTitle(title);
        conf.setLocation(location);
        conf.setDate(date);
<<<<<<< HEAD
        conf.setRole(role);
        conf.setUserId(user.getUser_id());
        conf.setDeptId(user.getDeptId());
=======
        conf.setDeptId(deptId);
>>>>>>> 459e049d54c61ab01b731c30e82a712e98f6abf9

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
                conf.setWordFileName(safeName);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Lỗi khi lưu file: " + e.getMessage());
            }
        }

        conferenceService.insertConference(conf);
        return ResponseEntity.ok(conf);
    }

    @PutMapping("/conference/{id}")
    @ResponseBody
    public ResponseEntity<?> updateConference(
            @PathVariable("id") int id,
            @RequestPart("title") String title,
            @RequestPart(value = "location", required = false) String location,
            @RequestPart(value = "date", required = false) String date,
<<<<<<< HEAD
            @RequestPart(value = "role", required = false) String role,
=======
>>>>>>> 459e049d54c61ab01b731c30e82a712e98f6abf9
            @RequestPart(value = "deptId", required = false) Integer deptId,
            @RequestPart(value = "wordFile", required = false) MultipartFile wordFile,
            @RequestPart(value = "wordFileName", required = false) String currentFileName
    ) {
        Conference existing = conferenceService.getConferenceById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        existing.setTitle(title);
        existing.setLocation(location);
        existing.setDate(date);
        existing.setDeptId(deptId);
<<<<<<< HEAD
        existing.setRole(role);
=======
>>>>>>> 459e049d54c61ab01b731c30e82a712e98f6abf9

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

        conferenceService.updateConference(existing);
        return ResponseEntity.ok(existing);
    }

    @DeleteMapping("/conference/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteConference(@PathVariable("id") int id) {
        Conference existing = conferenceService.getConferenceById(id);
        if (existing != null && existing.getWordFileName() != null) {
            try {
                Path filePath = Paths.get(UPLOAD_DIR).resolve(existing.getWordFileName());
                Files.deleteIfExists(filePath);
            } catch (IOException ignored) {}
        }
        conferenceService.deleteConference(id);
        return ResponseEntity.ok().build();
    }
}