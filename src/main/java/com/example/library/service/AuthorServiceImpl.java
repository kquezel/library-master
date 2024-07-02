package com.example.library.service;

import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    private static final ThreadLocal<SimpleDateFormat> dateFormat =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("dd.MM.yyyy"));

    @Override
    public void create(Author author) {
        authorRepository.save(author);
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Optional<Author> findById(long id) { return authorRepository.findById(id);}

    @Override
    public Author update(Long authorId, String fullName, String birth,
                         String biography) throws ParseException {
        Author author = authorRepository.findById(authorId).get();
        Date birthDate = dateFormat.get().parse(birth);
        author.setFullName(fullName);
        author.setBirth(birthDate);
        author.setBiography(biography);

        return authorRepository.save(author);
    }

    @Override
    public boolean delete(long id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Author> findbyKeyword(String keyword) {
        return authorRepository.findByKeyword(keyword);
    }
}