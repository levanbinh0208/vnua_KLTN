package com.vnua.service;

import com.vnua.mapper.BookMapper;
import com.vnua.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookMapper bookMapper;

    public BookService(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    public List<Book> getBooks() {
        return bookMapper.getBooks();
    }
}
