package com.example.library.service;

import com.example.library.model.Author;
import com.example.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public void create(Author author) {
        authorRepository.save(author);
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Optional<Author> findById(long author_id) { return authorRepository.findById(author_id);}


    @Override
    public Author read(long author_id) {
        return authorRepository.getOne(author_id);
    }

    @Override
    public boolean update(Author author, long author_id) {
        if (authorRepository.existsById(author_id)) {
            author.setAuthorId(author_id);
            authorRepository.save(author);
            return true;
        }

        return false;
    }

    @Override
    public boolean delete(long author_id) {
        if (authorRepository.existsById(author_id)) {
            authorRepository.deleteById(author_id);
            return true;
        }
        return false;
    }

    public List<Author> findbyKeyword(String keyword) {
        return authorRepository.findByKeyword(keyword);
    }
}