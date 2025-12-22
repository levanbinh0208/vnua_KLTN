package com.vnua.mapper;

import com.vnua.model.Book;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookMapper {
    List<Book> getBooks();

    Book findById(int id);

    void insertBook(Book book);

    void updateBook(Book book);

    void deleteBook(int id);

    List<Book> getByStatus(int status);

    void updateStatus(@Param("id") int id,
                      @Param("status") int status);
}
