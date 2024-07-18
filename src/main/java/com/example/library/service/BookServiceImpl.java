package com.example.library.service;

import com.example.library.dto.BookDto;
import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.model.User;
import com.example.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorService authorService;

    private static final ThreadLocal<SimpleDateFormat> dateFormat =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("dd.MM.yyyy"));


    @Override
    public void create(Book book) {
        bookRepository.save(book);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }


    @Override
    public Optional<Book> findById(long id) { return bookRepository.findById(id);}

    @Override
    public void update(BookDto bookDto) throws ParseException {
        Date publicationDate = dateFormat.get().parse(bookDto.getPublication());

        Book book = bookRepository.findByUuid(bookDto.getGuid()).orElseGet(Book::new);
        Optional<Author> author = authorService.findById(bookDto.getAuthorId());
        book.setName(bookDto.getName());
        book.setPublication(publicationDate);
        book.setGenre(bookDto.getGenre());
        book.setAuthor(author.get());
        if(book.getGuid() == null) {
            book.setGuid(UUID.randomUUID());
        } else {
            book.setGuid(bookDto.getGuid());
        }
        bookRepository.save(book);
    }


    @Override
    public boolean delete(long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Book> findByKeyword(String keyword) {
        return bookRepository.findByKeyword(keyword);
    }

}