package com.vnua.mapper;

import com.vnua.model.Book;

import java.util.List;

public interface BookMapper {
    List<Book> getBooks();

    Book findById(int id);

    void insertBook(Book book);

    void updateBook(Book book);

    void deleteBook(int id);

    List<Book> getByStatus(int status);

    void updateStatus(int id, int status);
}
