package com.example.library.service;

import com.example.library.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    void create(Author author);
    List<Author> findAll();
    Optional<Author> findById(long author_id);
    Author read(long author_id);
    boolean update(Author author, long author_id);
    boolean delete(long author_id);

}
