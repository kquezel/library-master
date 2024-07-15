package com.example.library.service;

import com.example.library.dto.AuthorDto;
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
import java.util.UUID;

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

    @Override
    public void update(AuthorDto authorDto) throws ParseException {
        Author author;
        Date birthDate = dateFormat.get().parse(authorDto.getBirth());
        long id = 0;
        if (authorDto.getGuid() != null) {
            id = Long.parseLong(String.valueOf(authorDto.getGuid()));
        }
        author = authorRepository.findById(id).orElseGet(Author::new);
        Author authorUuid = authorRepository.findByUuid(authorDto.getGuid());
        author.setFullName(authorDto.getFullName());
        author.setBirth(birthDate);
        author.setBiography(authorDto.getBiography());
        if(author.getGuid() == null) {
            author.setGuid(UUID.randomUUID());
        } else {
            author.setGuid(authorUuid.getGuid());
        }
        authorRepository.save(author);
    }
}