//package com.example.library.api.service;
//
//
//import com.example.library.dto.BookDto;
//import com.example.library.model.Author;
//import com.example.library.model.Book;
//import com.example.library.repository.BookRepository;
//import com.example.library.service.BookServiceImpl;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class BookServiceImplTests {
//
//    private static final ThreadLocal<SimpleDateFormat> dateFormat =
//            ThreadLocal.withInitial(() -> new SimpleDateFormat("dd.MM.yyyy"));
//
//
//    @Mock
//    private BookRepository bookRepository;
//
//    @InjectMocks
//    private BookServiceImpl bookService;
//
//    @Test
//    public void BookService_CreateBook_ReturnsBookDto() throws ParseException {
//        Book book = Book.builder()
//                .name("Book 1")
//                .publication(dateFormat.get().parse("14.08.2003"))
//                .genre("Novel").build();
//        BookDto bookDto = BookDto.builder()
//                .name("Book 1")
//                .publication(String.valueOf(dateFormat.get().parse("14.08.2003")))
//                .genre("Novel").build();
//
//        when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);
//
////        BookDto savedBook = bookService.create(bookDto,);
////
//        Assertions.assertThat(savedBook);
//
////        bookService.create(book);
//
////        Assertions.assertThat(bookService.create(book));
//
//    }
//
//}
