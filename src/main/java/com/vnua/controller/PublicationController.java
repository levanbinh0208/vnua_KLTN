package com.vnua.controller;

import com.vnua.model.Publication;
import com.vnua.service.PublicationService;
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
public class PublicationController {

    private final PublicationService publicationService;

    private static final String UPLOAD_DIR = "uploads/publications/";

    public PublicationController(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    @GetMapping("/publication")
    @ResponseBody
    public List<Publication> getAllPublications() {
        return publicationService.getAllPublications();
    }

    @GetMapping("/publication/{id}")
    @ResponseBody
    public ResponseEntity<?> getPublicationById(@PathVariable("id") int id) {
        Publication publication = publicationService.getPublicationById(id);
        if (publication == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(publication);
    }

    @GetMapping("/publication/{id}/file")
    public ResponseEntity<Resource> downloadFile(@PathVariable("id") int id) {
        Publication publication = publicationService.getPublicationById(id);
        if (publication == null || publication.getWordFileName() == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            Path filePath = Paths.get(UPLOAD_DIR).resolve(publication.getWordFileName()).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                String contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
                if (publication.getWordFileName().toLowerCase().endsWith(".doc")) {
                    contentType = "application/msword";
                }

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION,
                                "attachment; filename=\"" + StringUtils.cleanPath(publication.getWordFileName()) + "\"")
                        .header(HttpHeaders.CONTENT_TYPE, contentType)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/publication")
    @ResponseBody
    public ResponseEntity<?> insertPublication(
            @RequestPart("title") String title,
            @RequestPart(value = "authors", required = false) String authors,
            @RequestPart(value = "journal", required = false) String journal,
            @RequestPart(value = "year", required = false) String yearStr,
            @RequestPart(value = "wordFile", required = false) MultipartFile wordFile
    ) {
        Publication pub = new Publication();
        pub.setTitle(title);
        pub.setAuthors(authors);
        pub.setJournal(journal);
        if (yearStr != null && !yearStr.isEmpty()) {
            try {
                pub.setYear(Integer.parseInt(yearStr));
            } catch (NumberFormatException e) {
            }
        }

        if (wordFile != null && !wordFile.isEmpty()) {
            String originalName = StringUtils.cleanPath(wordFile.getOriginalFilename());
            if (!originalName.toLowerCase().endsWith(".doc") && !originalName.toLowerCase().endsWith(".docx")) {
                return ResponseEntity.badRequest().body("Chỉ hỗ trợ file .doc hoặc .docx");
            }

            String extension = originalName.substring(originalName.lastIndexOf("."));
            String safeName = UUID.randomUUID() + extension;
            Path uploadPath = Paths.get(UPLOAD_DIR);

            try {
                Files.createDirectories(uploadPath);
                Path filePath = uploadPath.resolve(safeName);
                Files.copy(wordFile.getInputStream(), filePath);
                pub.setWordFileName(safeName);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Lỗi khi lưu file: " + e.getMessage());
            }
        }

        publicationService.insertPublication(pub);
        return ResponseEntity.ok(pub);
    }

    @PutMapping("/publication/{id}")
    @ResponseBody
    public ResponseEntity<?> updatePublication(
            @PathVariable("id") int id,
            @RequestPart("title") String title,
            @RequestPart(value = "authors", required = false) String authors,
            @RequestPart(value = "journal", required = false) String journal,
            @RequestPart(value = "year", required = false) String yearStr,
            @RequestPart(value = "wordFile", required = false) MultipartFile wordFile,
            @RequestPart(value = "wordFileName", required = false) String currentFileName
    ) {
        Publication existing = publicationService.getPublicationById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        existing.setTitle(title);
        existing.setAuthors(authors);
        existing.setJournal(journal);
        if (yearStr != null && !yearStr.isEmpty()) {
            try {
                existing.setYear(Integer.parseInt(yearStr));
            } catch (NumberFormatException ignored) {}
        }

        if (wordFile != null && !wordFile.isEmpty()) {
            String originalName = StringUtils.cleanPath(wordFile.getOriginalFilename());
            if (!originalName.toLowerCase().endsWith(".doc") && !originalName.toLowerCase().endsWith(".docx")) {
                return ResponseEntity.badRequest().body("Chỉ hỗ trợ file .doc hoặc .docx");
            }

            if (existing.getWordFileName() != null) {
                try {
                    Path oldPath = Paths.get(UPLOAD_DIR).resolve(existing.getWordFileName());
                    Files.deleteIfExists(oldPath);
                } catch (IOException e) {
                }
            }

            String extension = originalName.substring(originalName.lastIndexOf("."));
            String safeName = UUID.randomUUID() + extension;
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

        publicationService.updatePublication(existing);
        return ResponseEntity.ok(existing);
    }

    @DeleteMapping("/publication/{id}")
    @ResponseBody
    public ResponseEntity<?> deletePublication(@PathVariable("id") int id) {
        Publication existing = publicationService.getPublicationById(id);
        if (existing != null && existing.getWordFileName() != null) {
            try {
                Path filePath = Paths.get(UPLOAD_DIR).resolve(existing.getWordFileName());
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                // Không fail nếu xóa file không thành — vẫn xóa DB
            }
        }
        publicationService.deletePublication(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/authors")
    @ResponseBody
    public ResponseEntity<List<String>> getAuthors() {
        List<String> authors = publicationService.getAuthors();
        return ResponseEntity.ok(authors);
    }
}