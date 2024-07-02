package com.example.library.service;

import com.example.library.model.Author;
import com.example.library.model.Book;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BookService {
    void create(Book book);
    List<Book> findAll();
    Optional<Book> findById(long id);
    Book update(Long id, String name, String publication,
                   String genre, String author) throws ParseException;
    boolean delete(long id);

    List<Book> findbyKeyword(String keyword);

}
