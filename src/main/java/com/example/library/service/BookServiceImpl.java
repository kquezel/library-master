package com.example.library.service;

import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;


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
    public boolean update(Book book, long id) {
        if (bookRepository.existsById(id)) {
            book.setId(id);
            bookRepository.save(book);
            return true;
        }

        return false;
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