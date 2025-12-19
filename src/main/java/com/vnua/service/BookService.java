package com.vnua.service;

import com.vnua.mapper.BookMapper;
import com.vnua.model.Book;
import com.vnua.model.Conference;
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


    public Book getBookById(int id) {
        return bookMapper.findById(id);
    }

    public void insertBook(Book book) {
        bookMapper.insertBook(book);
    }

    public void updateBook(Book book) {
        bookMapper.updateBook(book);
    }

    public void deleteBook(int id) {
        bookMapper.deleteBook(id);
    }

    public List<Book> getByStatus(int status) {
        return bookMapper.getByStatus(status);
    }

    public void updateStatus(int id, int status) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID không hợp lệ: " + id);
        }
        bookMapper.updateStatus(id, status);
    }
}
