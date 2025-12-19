package com.vnua.controller;

import com.vnua.model.Patent;
import com.vnua.model.SysUser;
import com.vnua.service.PatentService;
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
public class PatentController {

    private final PatentService patentService;

    private static final String UPLOAD_DIR = "uploads/patents/";

    public PatentController(PatentService patentService) {
        this.patentService = patentService;
    }

    @GetMapping("/patent")
    @ResponseBody
    public List<Patent> getPatents() {
        return patentService.getPatents();
    }

    @GetMapping("/patent/{id}")
    @ResponseBody
    public ResponseEntity<?> getPatentById(@PathVariable("id") int id) {
        Patent patent = patentService.getPatentById(id);
        if (patent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(patent);
    }

    @GetMapping("/patent/{id}/file")
    public ResponseEntity<Resource> downloadFile(@PathVariable("id") int id) {
        Patent patent = patentService.getPatentById(id);
        if (patent == null || patent.getWordFileName() == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            Path filePath = Paths.get(UPLOAD_DIR).resolve(patent.getWordFileName()).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                String contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
                if (patent.getWordFileName().toLowerCase().endsWith(".doc")) {
                    contentType = "application/msword";
                }

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION,
                                "attachment; filename=\"" + StringUtils.cleanPath(patent.getWordFileName()) + "\"")
                        .header(HttpHeaders.CONTENT_TYPE, contentType)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/patent")
    @ResponseBody
    public ResponseEntity<?> insertPatent(
            @RequestPart("title") String title,
            @RequestPart(value = "patentNo", required = false) String patentNo,
            @RequestPart(value = "year", required = false) String yearStr,
            @RequestPart(value = "status", required = false) String status,
            @RequestPart(value = "wordFile", required = false) MultipartFile wordFile
            , HttpSession session
    ) {
        SysUser user = (SysUser) session.getAttribute("loggedInUser");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Patent patent = new Patent();
        patent.setTitle(title);
        patent.setPatentNo(patentNo);
        patent.setStatus(status);
        patent.setUserId(user.getUser_id());
        patent.setDeptId(user.getDeptId());
        if (yearStr != null && !yearStr.isEmpty()) {
            try {
                patent.setYear(Integer.parseInt(yearStr));
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
                patent.setWordFileName(safeName);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Lỗi khi lưu file: " + e.getMessage());
            }
        }

        patentService.insertPatent(patent);
        return ResponseEntity.ok(patent);
    }

    @PutMapping("/patent/{id}")
    @ResponseBody
    public ResponseEntity<?> updatePatent(
            @PathVariable("id") int id,
            @RequestPart("title") String title,
            @RequestPart(value = "patentNo", required = false) String patentNo,
            @RequestPart(value = "year", required = false) String yearStr,
            @RequestPart(value = "status", required = false) String status,
            @RequestPart(value = "wordFile", required = false) MultipartFile wordFile,
            @RequestPart(value = "wordFileName", required = false) String currentFileName
    ) {
        Patent existing = patentService.getPatentById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        existing.setTitle(title);
        existing.setPatentNo(patentNo);
        existing.setStatus(status);
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

        patentService.updatePatent(existing);
        return ResponseEntity.ok(existing);
    }

    @DeleteMapping("/patent/{id}")
    @ResponseBody
    public ResponseEntity<?> deletePatent(@PathVariable("id") int id) {
        Patent existing = patentService.getPatentById(id);
        if (existing != null && existing.getWordFileName() != null) {
            try {
                Path filePath = Paths.get(UPLOAD_DIR).resolve(existing.getWordFileName());
                Files.deleteIfExists(filePath);
            } catch (IOException ignored) {}
        }
        patentService.deletePatent(id);
        return ResponseEntity.ok().build();
    }
}