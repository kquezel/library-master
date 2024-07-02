package com.example.library.service;

import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    AuthorService authorService;

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
    public Book update(Long bookId, String name, String publication,
                       String genre, String author) throws ParseException {
        Book book = bookRepository.findById(bookId).get();
        Date publicationDate = dateFormat.get().parse(publication);
        Optional<Author> authorId = authorService.findById(Long.parseLong(author));
        book.setName(name);
        book.setPublication(publicationDate);
        book.setGenre(genre);
        book.setAuthor(authorId.get());
        return bookRepository.save(book);
    }

    @Override
    public boolean delete(long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Book> findbyKeyword(String keyword) {
        return bookRepository.findByKeyword(keyword);
    }
}