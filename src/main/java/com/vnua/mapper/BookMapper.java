package com.vnua.mapper;

import com.vnua.model.Book;
<<<<<<< HEAD
import org.apache.ibatis.annotations.Param;
=======
>>>>>>> 459e049d54c61ab01b731c30e82a712e98f6abf9

import java.util.List;

public interface BookMapper {
    List<Book> getBooks();

    Book findById(int id);

    void insertBook(Book book);

    void updateBook(Book book);

    void deleteBook(int id);

    List<Book> getByStatus(int status);

<<<<<<< HEAD
    void updateStatus(@Param("id") int id,
                      @Param("status") int status);

=======
    void updateStatus(int id, int status);
>>>>>>> 459e049d54c61ab01b731c30e82a712e98f6abf9
}
