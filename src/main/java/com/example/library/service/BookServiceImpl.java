package com.example.library.service;

import com.example.library.dto.BookDto;
import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.model.User;
import com.example.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
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
    public BookDto update(BookDto bookDto) throws ParseException {
        Book book;
        Date publicationDate = dateFormat.get().parse(bookDto.getPublication());
        long id = 0;
        if (bookDto.getGuid() != null && !bookDto.getGuid().isEmpty()) {
            id = Long.parseLong(bookDto.getGuid());
        }
        book = bookRepository.findById(id).orElseGet(Book::new);
        Optional<Author> author = authorService.findById(bookDto.getAuthor().getId());
        book.setName(bookDto.getName());
        book.setPublication(publicationDate);
        book.setGenre(bookDto.getGenre());
        book.setAuthor(author.get());

        Book newBook = bookRepository.save(book);

        BookDto newBookDto = new BookDto();
        newBookDto.setName(newBook.getName());
        newBookDto.setPublication(newBook.getPublication().toString());
        newBookDto.setGenre(newBook.getGenre());
        newBookDto.setAuthor(newBook.getAuthor());
        return newBookDto;
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