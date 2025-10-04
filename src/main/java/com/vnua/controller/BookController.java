package com.vnua.controller;

import com.vnua.model.Book;
import com.vnua.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/book")
    public String bookPage() {
        return "indexUser";
    }

    @GetMapping("/api/book")
    @ResponseBody
    public List<Book> getBooks() {
        return bookService.getBooks();
    }
}
