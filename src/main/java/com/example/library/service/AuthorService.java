package com.example.library.service;

import com.example.library.dto.AuthorDto;
import com.example.library.model.Author;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface AuthorService {
    void create(Author author);
    List<Author> findAll();
    Optional<Author> findById(long id);
//    Author update(Long authorId, String fullName, String birth,
//                  String biography) throws ParseException;
    boolean delete(long id);

    List<Author> findbyKeyword(String keyword);

    void update(AuthorDto authorDto) throws ParseException;
}
