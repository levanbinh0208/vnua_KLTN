package com.vnua.controller;

import com.vnua.model.Book;
import com.vnua.service.BookService;
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
public class BookController {

    private final BookService bookService;

    // Thư mục lưu file sách
    private static final String UPLOAD_DIR = "uploads/books/";

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/book")
    @ResponseBody
    public List<Book> getBooks() {
        return bookService.getBooks();
    }

    @GetMapping("/book/{id}")
    @ResponseBody
    public ResponseEntity<?> getBookById(@PathVariable("id") int id) {
        Book book = bookService.getBookById(id);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
    }

    @GetMapping("/book/{id}/file")
    public ResponseEntity<Resource> downloadFile(@PathVariable("id") int id) {
        Book book = bookService.getBookById(id);
        if (book == null || book.getWordFileName() == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            Path filePath = Paths.get(UPLOAD_DIR).resolve(book.getWordFileName()).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                String contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
                if (book.getWordFileName().toLowerCase().endsWith(".doc")) {
                    contentType = "application/msword";
                }

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION,
                                "attachment; filename=\"" + StringUtils.cleanPath(book.getWordFileName()) + "\"")
                        .header(HttpHeaders.CONTENT_TYPE, contentType)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/book")
    @ResponseBody
    public ResponseEntity<?> insertBook(
            @RequestPart("title") String title,
            @RequestPart(value = "publisher", required = false) String publisher,
            @RequestPart(value = "year", required = false) String yearStr,
            @RequestPart(value = "isbn", required = false) String isbn,
            @RequestPart(value = "wordFile", required = false) MultipartFile wordFile
    ) {
        Book book = new Book();
        book.setTitle(title);
        book.setPublisher(publisher);
        book.setIsbn(isbn);
        if (yearStr != null && !yearStr.isEmpty()) {
            try {
                book.setYear(Integer.parseInt(yearStr));
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
                book.setWordFileName(safeName);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Lỗi khi lưu file: " + e.getMessage());
            }
        }

        bookService.insertBook(book);
        return ResponseEntity.ok(book);
    }

    @PutMapping("/book/{id}")
    @ResponseBody
    public ResponseEntity<?> updateBook(
            @PathVariable("id") int id,
            @RequestPart("title") String title,
            @RequestPart(value = "publisher", required = false) String publisher,
            @RequestPart(value = "year", required = false) String yearStr,
            @RequestPart(value = "isbn", required = false) String isbn,
            @RequestPart(value = "wordFile", required = false) MultipartFile wordFile,
            @RequestPart(value = "wordFileName", required = false) String currentFileName
    ) {
        Book existing = bookService.getBookById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        existing.setTitle(title);
        existing.setPublisher(publisher);
        existing.setIsbn(isbn);
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

        bookService.updateBook(existing);
        return ResponseEntity.ok(existing);
    }

    @DeleteMapping("/book/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteBook(@PathVariable("id") int id) {
        Book existing = bookService.getBookById(id);
        if (existing != null && existing.getWordFileName() != null) {
            try {
                Path filePath = Paths.get(UPLOAD_DIR).resolve(existing.getWordFileName());
                Files.deleteIfExists(filePath);
            } catch (IOException ignored) {}
        }
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }
}