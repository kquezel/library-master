package com.example.library.service;

import com.example.library.model.Author;
import com.example.library.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    void create(Book book);
    List<Book> findAll();
    Optional<Book> findById(long book_id);
    Book getById(Long book_id);
    Book read(long book_id);
    boolean update(Book book, long book_id);
    boolean delete(long book_id);

    List<Book> findbyKeyword(String keyword);

}
