package com.example.library.service;

import com.example.library.model.Author;
import com.example.library.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    void create(Book book);
    List<Book> findAll();
    Optional<Book> findById(long id);
    boolean update(Book book, long id);
    boolean delete(long id);

    List<Book> findbyKeyword(String keyword);

}
