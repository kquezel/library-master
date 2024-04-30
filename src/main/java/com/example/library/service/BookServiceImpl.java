package com.example.library.service;

import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Optional<Book> findById(long book_id) { return bookRepository.findById(book_id);}

    @Override
    public Book getById(Long book_id) { return  bookRepository.getById(book_id); }

    @Override
    public Book read(long book_id) {
        return bookRepository.getOne(book_id);
    }

    @Override
    public boolean update(Book book, long book_id) {
        if (bookRepository.existsById(book_id)) {
            book.setBookId(book_id);
            bookRepository.save(book);
            return true;
        }

        return false;
    }

    @Override
    public boolean delete(long book_id) {
        if (bookRepository.existsById(book_id)) {
            bookRepository.deleteById(book_id);
            return true;
        }
        return false;
    }

    public List<Book> findbyKeyword(String keyword) {
        return bookRepository.findByKeyword(keyword);
    }
}