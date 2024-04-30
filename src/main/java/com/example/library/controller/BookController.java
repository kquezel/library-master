package com.example.library.controller;

import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.model.User;
import com.example.library.service.AuthorService;
import com.example.library.service.BookService;
import com.example.library.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    //    @PostMapping("/book")
//    public String userAddBook(@RequestParam String book, Model model){
//        Optional<Book> bookId = bookService.findById(Long.parseLong(book));
//        if(bookId.isPresent()) {
//            User user = new User();
//            user.setBook(bookId.get());
//            usersService.create(user);
//        }
//        return "redirect:/library/book";
//    }
//    @GetMapping("/{user_id}/take/{book_id}")
//    public String takeBook(@PathVariable(value = "user_id") Long userId, @PathVariable(value = "book_id") Long bookId) {
//
//        User user = usersService.getById(userId);
//        Book book = bookService.getById(bookId);
//        user.setBook(book);
//        usersService.create(user);
//        return "book-main";
//    }
    @GetMapping("/book/{id}")
    public String getABookById(@PathVariable(value = "id") Long bookId, Model model) {
        Optional<Book> book = bookService.findById(bookId);
        ArrayList<Book> res = new ArrayList<>();
        book.ifPresent(res::add);
        model.addAttribute("book", res);
        return "book-details";
    }

    @PostMapping("/book/{id}/addBook")
    public String userAddBook(Model model, Principal principal, @RequestParam String action,
                              @PathVariable(value = "id") Long bookId) {
        User user = usersService.findUser(principal.getName());
        Optional<Book> optBook = bookService.findById(bookId);
        Book book = optBook.get();
        book.setUser(user);
        bookService.create(book);
        return "redirect:/library/book";
    }

    @PostMapping("/book/{id}/returnBook")
    public String userReturnBook(Model model, Principal principal, @RequestParam String action,
                                 @PathVariable(value = "id") Long bookId) {
        Optional<Book> optBook = bookService.findById(bookId);
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
        Optional<Author> authorId = authorService.findById(Long.parseLong(author));
        if (authorId.isPresent()) {
            Book book = new Book(name, publicationDate, genre);
            book.setAuthor(authorId.get());
            bookService.create(book);
        }
        return "redirect:/library/book";
    }

    @GetMapping("/book/{id}/edit")
    public String getBookEdit(@PathVariable(value = "id") Long bookId, Model model) {
        Optional<Book> book = bookService.findById(bookId);
        ArrayList<Book> res = new ArrayList<>();
        book.ifPresent(res::add);
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("book", res);
        return "book-edit";
    }

    @PostMapping("/book/{id}/edit")
    public String bookPostUpdate(@PathVariable(value = "id") Long bookId, @RequestParam String name,
                                 @RequestParam String publication, @RequestParam String genre,
                                 @RequestParam String author, Model model)
            throws ParseException {
        Book book = bookService.findById(bookId).orElseThrow();
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
    public String bookPostDelete(@PathVariable(value = "id") Long bookId, Model model) {
        Book book = bookService.findById(bookId).orElseThrow();
        bookService.delete(book.getBookId());
        return "redirect:/library/book";
    }


    @GetMapping("/take-book/{bookId}")
    public ResponseEntity<String> getTakeBookPage(@PathVariable Long bookId) {
        // Возможно, здесь вам нужно будет вернуть шаблон Thymeleaf для страницы с информацией о взятии книги
        return ResponseEntity.ok("Страница взятия книги");
    }

    @PostMapping("/take-book/{bookId}")
    public ResponseEntity<String> takeBook(@PathVariable Long bookId) {
        // Получите id пользователя, который выполнил вход, например, из вашего контекста безопасности
        String username = authentication.getName();
        User userId = usersService.findUser(username);// ... ваш способ получения id пользователя;

        // Ищем книгу по id
        Optional<Book> optionalBook = bookService.findById(bookId);

        if (optionalBook.isPresent()) {
            // Устанавливаем userId в книгу и сохраняем в базе данных
            Book book = optionalBook.get();
            book.setUser(userId);
            bookService.create(book);

            return ResponseEntity.ok("Книга успешно взята");
        } else
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Книга не найдена");
    }
}