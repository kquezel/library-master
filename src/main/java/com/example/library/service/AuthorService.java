package com.example.library.service;

import com.example.library.dto.AuthorDto;
import com.example.library.model.Author;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface AuthorService {
    void create(Author author);
    List<Author> findAll(int pageNo, int pageSize);
    Optional<Author> findById(long id);
    boolean delete(long id);

    List<Author> findByKeyword(String keyword);

    void update(AuthorDto authorDto) throws ParseException;
}
