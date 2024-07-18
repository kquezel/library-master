package com.example.library.service;

import com.example.library.dto.BookDto;
import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.model.User;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    private static final ThreadLocal<SimpleDateFormat> dateFormat =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("dd.MM.yyyy"));

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookServiceImpl;

    @Mock
    private AuthorService authorService;




    @Test
    void create() {
    }

    @Test
    void findAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void update() throws ParseException {

        UUID uuid = UUID.randomUUID();


        Author author = new Author("", dateFormat.get().parse("11.11.2011"), "");

        author.setId(1L);

        Book book = new Book("Example", dateFormat.get().parse("14.08.2003"), "Novel", author);

        BookDto bookDto = new BookDto(uuid,"Example", "14.08.2003", "Novel", 1L);

        Mockito.when(bookRepository.findByUuid(bookDto.getGuid())).thenReturn(Optional.of(book));

        Mockito.when(authorService.findById(1L)).thenReturn(Optional.of(author));


        Mockito.when(bookRepository.save(book)).thenReturn(book);


        bookServiceImpl.update(bookDto);

        verify(bookRepository, times(1)).findByUuid(bookDto.getGuid());
        ArgumentCaptor<Book> bookArg = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository, times(1)).save(bookArg.capture());
        Book bookResponse = bookArg.getValue();
        assertEquals("Example", bookResponse.getName());
        assertEquals(dateFormat.get().parse("14.08.2003"), bookResponse.getPublication());
        assertEquals("Novel", bookResponse.getGenre());
        assertEquals(author, bookResponse.getAuthor());


    }

    @Test
    void delete() {
    }

    @Test
    void findByKeyword() {
    }
}