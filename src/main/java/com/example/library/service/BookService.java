package com.example.library.service;

import com.example.library.dto.BookDto;
import com.example.library.model.Author;
import com.example.library.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BookService {
    void create(Book book);
    List<Book> findAll();
    Optional<Book> findById(long id);
    boolean delete(long id);

    List<Book> findByKeyword(String keyword);

    void update(BookDto bookDto) throws ParseException;
}
