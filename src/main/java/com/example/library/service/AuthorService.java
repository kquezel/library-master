package com.example.library.service;

import com.example.library.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    void create(Author author);
    List<Author> findAll();
    Optional<Author> findById(long id);
    boolean update(Author author, long id);
    boolean delete(long id);

    List<Author> findbyKeyword(String keyword);

}
