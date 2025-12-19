package com.vnua.controller;

import com.vnua.model.Supervision;
import com.vnua.model.SysUser;
import com.vnua.service.SupervisionService;
import jakarta.servlet.http.HttpSession;
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
import java.util.UUID;

@Controller
public class SupervisionController {

    private final SupervisionService supervisionService;

    private static final String UPLOAD_DIR = "uploads/supervisions/"; // Thư mục lưu file hướng dẫn

    public SupervisionController(SupervisionService supervisionService) {
        this.supervisionService = supervisionService;
    }

    @GetMapping("/supervision")
    @ResponseBody
    public List<Supervision> getSupervisions() {
        return supervisionService.getSupervisions();
    }

    @GetMapping("/supervision/{id}")
    @ResponseBody
    public ResponseEntity<?> getSupervisionById(@PathVariable("id") int id) {
        Supervision sup = supervisionService.getSupervisionById(id);
        if (sup == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sup);
    }

    @GetMapping("/supervision/{id}/file")
    public ResponseEntity<Resource> downloadFile(@PathVariable("id") int id) {
        Supervision sup = supervisionService.getSupervisionById(id);
        if (sup == null || sup.getWordFileName() == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            Path filePath = Paths.get(UPLOAD_DIR).resolve(sup.getWordFileName()).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                String contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
                if (sup.getWordFileName().toLowerCase().endsWith(".doc")) {
                    contentType = "application/msword";
                }

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION,
                                "attachment; filename=\"" + StringUtils.cleanPath(sup.getWordFileName()) + "\"")
                        .header(HttpHeaders.CONTENT_TYPE, contentType)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/supervision")
    @ResponseBody
    public ResponseEntity<?> insertSupervision(
            @RequestPart("studentName") String studentName,
            @RequestPart(value = "level", required = false) String level,
            @RequestPart(value = "thesisTitle", required = false) String thesisTitle,
            @RequestPart(value = "year", required = false) String yearStr,
            @RequestPart(value = "wordFile", required = false) MultipartFile wordFile
            , HttpSession session
    ) {
        SysUser user = (SysUser) session.getAttribute("loggedInUser");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Supervision sup = new Supervision();
        sup.setUserId(user.getUser_id());
        sup.setDeptId(user.getDeptId());
        sup.setStudentName(studentName);
        sup.setLevel(level);
        sup.setThesisTitle(thesisTitle);
        if (yearStr != null && !yearStr.isEmpty()) {
            try {
                sup.setYear(Integer.parseInt(yearStr));
            } catch (NumberFormatException ignored) {}
        }

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
                sup.setWordFileName(safeName);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Lỗi khi lưu file: " + e.getMessage());
            }
        }

        supervisionService.insertSupervision(sup);
        return ResponseEntity.ok(sup);
    }

    @PutMapping("/supervision/{id}")
    @ResponseBody
    public ResponseEntity<?> updateSupervision(
            @PathVariable("id") int id,
            @RequestPart("studentName") String studentName,
            @RequestPart(value = "level", required = false) String level,
            @RequestPart(value = "thesisTitle", required = false) String thesisTitle,
            @RequestPart(value = "year", required = false) String yearStr,
            @RequestPart(value = "wordFile", required = false) MultipartFile wordFile,
            @RequestPart(value = "wordFileName", required = false) String currentFileName
    ) {
        Supervision existing = supervisionService.getSupervisionById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        existing.setStudentName(studentName);
        existing.setLevel(level);
        existing.setThesisTitle(thesisTitle);
        if (yearStr != null && !yearStr.isEmpty()) {
            try {
                existing.setYear(Integer.parseInt(yearStr));
            } catch (NumberFormatException ignored) {}
        }

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

        supervisionService.updateSupervision(existing);
        return ResponseEntity.ok(existing);
    }

    @DeleteMapping("/supervision/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteSupervision(@PathVariable("id") int id) {
        Supervision existing = supervisionService.getSupervisionById(id);
        if (existing != null && existing.getWordFileName() != null) {
            try {
                Path filePath = Paths.get(UPLOAD_DIR).resolve(existing.getWordFileName());
                Files.deleteIfExists(filePath);
            } catch (IOException ignored) {}
        }
        supervisionService.deleteSupervision(id);
        return ResponseEntity.ok().build();
    }
}