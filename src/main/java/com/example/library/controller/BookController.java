package com.example.library.controller;

import com.example.library.dto.BookDto;
import com.example.library.model.Book;
import com.example.library.model.User;
import com.example.library.repository.BookRepository;
import com.example.library.service.AuthorService;
import com.example.library.service.BookService;
import com.example.library.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/library")
public class BookController {
    private static final ThreadLocal<SimpleDateFormat> dateFormat =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("dd.MM.yyyy"));
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    @Autowired
    BookService bookService;
    @Autowired
    AuthorService authorService;
    @Autowired
    UsersService usersService;
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/book")
    public String getAllBooks(Model model, Principal principal, String keyword) {
        User user = usersService.findUser(principal.getName());
        List<Book> books = bookService.findAll();

//        Pageable firstPageWithTwoElements = PageRequest.of(0, 2, Sort.by("fullName").descending());
//
//        Page<Book> pageBook = bookRepository.findAll(firstPageWithTwoElements);

        model.addAttribute("users", user);
        books.sort((s1, s2) -> CharSequence.compare(s1.getName(), s2.getName()));



        if(keyword != null) {
            model.addAttribute("books", bookService.findByKeyword(keyword));
        }
        else {
            model.addAttribute("books", books);
        }
        return "book-main";
    }


    @GetMapping("/book/{id}")
    public String getABookById(@PathVariable(value = "id") Long id, Model model) {
        Optional<Book> bookId = bookService.findById(id);
        ArrayList<Book> books = new ArrayList<>();
        bookId.ifPresent(books::add);
        model.addAttribute("book", books);
        return "book-details";
    }

    @PostMapping("/book/{id}/add-book")
    public String userAddBook(Principal principal, @PathVariable(value = "id") Long id) throws ParseException {
        User user = usersService.findUser(principal.getName());
        Optional<Book> optBook = bookService.findById(id);
        Book book = optBook.get();
        book.setUser(user);
        bookService.create(book);
        return "redirect:/library/book";
    }

    @PostMapping("/book/{id}/return-book")
    public String userReturnBook(@PathVariable(value = "id") Long id, Principal principal) throws ParseException {
        Optional<Book> optBook = bookService.findById(id);
        Book book = optBook.get();
        book.setUserNULL();
        bookService.create(book);
        return "redirect:/library/book";
    }


    @GetMapping("/book/{id}/edit")
    public String getBookEdit(@PathVariable(value = "id") Long id, Model model) throws ParseException {
        BookDto bookDto = new BookDto();
        Optional<Book> bookOptional = bookService.findById(id);
        if(bookOptional.isPresent()) {
            Book book = bookOptional.get();
            bookDto.setGuid(book.getGuid());
            bookDto.setName(book.getName());
            bookDto.setGenre(book.getGenre());
            bookDto.setPublication(dateFormat.get().format(book.getPublication()));
            bookDto.setAuthorId(book.getAuthor().getId());
        }

        model.addAttribute("book", bookDto);
//        model.addAttribute("authors", authorService.findAll());
//        model.addAttribute("pageTitle", "Добавление книги");
        return "book-edit";

    }

    @PostMapping("/book/edit")
    public String bookPostUpdate(@Valid @ModelAttribute("book") BookDto bookDto, BindingResult result,
                                 Model model) throws ParseException {
        if (result.hasErrors()){
            model.addAttribute("book", bookDto);
//            model.addAttribute("authors", authorService.findAll());
            model.addAttribute("pageTitle", "Редактирование книги");
            return "book-edit";
        }
        bookService.update(bookDto);

        return "redirect:/library/book";
    }

    @PostMapping("/book/{id}/remove")
    public String bookPostDelete(@PathVariable(value = "id") Long id) {
        Book book = bookService.findById(id).orElseThrow();
        bookService.delete(book.getId());
        return "redirect:/library/book";
    }
}