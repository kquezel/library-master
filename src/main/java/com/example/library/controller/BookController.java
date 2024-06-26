package com.example.library.controller;

import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.model.User;
import com.example.library.service.AuthorService;
import com.example.library.service.BookService;
import com.example.library.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping("/book")
    public String getAllBooks(Model model, Principal principal, String keyword) {
        User user = usersService.findUser(principal.getName());
        List<Book> books = bookService.findAll();

        model.addAttribute("users", user);
        books.sort(new Comparator<Book>() {
            @Override
            public int compare(Book s1, Book s2) {
                return CharSequence.compare(s1.getName(), s2.getName());
            }
        });

        if(keyword != null) {
            model.addAttribute("books", bookService.findbyKeyword(keyword));
        }
        else {
            model.addAttribute("books", books);
        }
        return "book-main";
    }

    @GetMapping("/book/{id}")
    public String getABookById(@PathVariable(value = "id") Long id, Model model) {
        Optional<Book> book = bookService.findById(id);
        ArrayList<Book> res = new ArrayList<>();
        book.ifPresent(res::add);
        model.addAttribute("book", res);
        return "book-details";
    }

    @PostMapping("/book/{id}/add-book")
    public String userAddBook(Principal principal, @PathVariable(value = "id") Long id) {
        User user = usersService.findUser(principal.getName());
        Optional<Book> optBook = bookService.findById(id);
        Book book = optBook.get();
        book.setUser(user);
        bookService.create(book);
        return "redirect:/library/book";
    }

    @PostMapping("/book/{id}/return-book")
    public String userReturnBook(@PathVariable(value = "id") Long id) {
        Optional<Book> optBook = bookService.findById(id);
        Book book = optBook.get();
        book.setUserNULL();
        bookService.create(book);
        return "redirect:/library/book";
    }


    @GetMapping("/book/add")
    public String bookAdd(Model model) {
        model.addAttribute("authors", authorService.findAll());
        return "book-add";
    }

    @PostMapping("/book/add")
    public String bookPostAdd(@RequestParam String name, @RequestParam String publication, @RequestParam String genre,
                              @RequestParam String author, Model model) throws ParseException {
        Date publicationDate = dateFormat.get().parse(publication);
        Optional<Author> id = authorService.findById(Long.parseLong(author));
        if (id.isPresent()) {
            Book book = new Book(name, publicationDate, genre);
            book.setAuthor(id.get());
            bookService.create(book);
        }
        return "redirect:/library/book";
    }

    @GetMapping("/book/{id}/edit")
    public String getBookEdit(@PathVariable(value = "id") Long id, Model model) {
        Optional<Book> book = bookService.findById(id);
        ArrayList<Book> res = new ArrayList<>();
        book.ifPresent(res::add);
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("book", res);
        return "book-edit";
    }

    @PostMapping("/book/{id}/edit")
    public String bookPostUpdate(@PathVariable(value = "id") Long id, @RequestParam String name,
                                 @RequestParam String publication, @RequestParam String genre,
                                 @RequestParam String author, Model model)
            throws ParseException {
        Book book = bookService.findById(id).orElseThrow();
        Date publicationDate = dateFormat.get().parse(publication);
        Optional<Author> authorId = authorService.findById(Long.parseLong(author));
        if (authorId.isPresent()) {
            book.setAuthor(authorId.get());
            bookService.create(book);
        }
        book.setName(name);
        book.setPublication(publicationDate);
        book.setGenre(genre);
        model.addAttribute("book", book);
        model.addAttribute("result", "Success update");
        return "book-details";
    }

    @PostMapping("/book/{id}/remove")
    public String bookPostDelete(@PathVariable(value = "id") Long id, Model model) {
        Book book = bookService.findById(id).orElseThrow();
        bookService.delete(book.getId());
        return "redirect:/library/book";
    }
}