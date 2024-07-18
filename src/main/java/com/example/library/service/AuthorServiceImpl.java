package com.example.library.service;

import com.example.library.dto.AuthorDto;
import com.example.library.model.Author;
import com.example.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public List<Author> findAll(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Author> authors = authorRepository.findAll(pageable);
        return authors.getContent();

    }

    @Override
    public Optional<Author> findById(long id) { return authorRepository.findById(id);}


    @Override
    public boolean delete(long id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Author> findByKeyword(String keyword) {
        return authorRepository.findByKeyword(keyword);
    }

    @Override
    public void update(AuthorDto authorDto) throws ParseException {
        Date birthDate = dateFormat.get().parse(authorDto.getBirth());
//        UUID uuid = null;
//        if (authorDto.getGuid() != null ) {
//            uuid = authorDto.getGuid();
//        }
        Author author = authorRepository.findByUuid(authorDto.getGuid()).orElseGet(Author::new);
        author.setFullName(authorDto.getFullName());
        author.setBirth(birthDate);
        author.setBiography(authorDto.getBiography());
        if(author.getGuid() == null) {
            author.setGuid(UUID.randomUUID());
        } else {
            author.setGuid(authorDto.getGuid());
        }
        authorRepository.save(author);
    }
}