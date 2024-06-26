package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.model.User;
import com.example.library.model.enums.Role;
import com.example.library.service.BookService;
import com.example.library.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/library")
public class UsersController {
    private static final ThreadLocal<SimpleDateFormat> dateFormat =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("dd.MM.yyyy"));
    @Autowired
    UsersService usersService;

    @Autowired
    BookService bookService;

    @GetMapping(value = "/{username}", produces = "application/json")
    public String userInfo(@PathVariable String username) {
        return usersService.findUser(username).toString();
    }

    @GetMapping("/user")
    public String getAllUsers(Model model) {
        List<User> users = usersService.findAll();
        List<Book> book = bookService.findAll();
        model.addAttribute("books", book);
        model.addAttribute("users", users);
        return "user-main";
    }

    @GetMapping("/user/{id}")
    public String getUsersById(@PathVariable(value = "id") Long id, Model model) {
        Optional<User> user = usersService.findById(id);
        ArrayList<User> res = new ArrayList<>();
        user.ifPresent(res::add);
        model.addAttribute("user", res);
        return "user-details";
    }

    @GetMapping("/")
    public String getUserBook(Model model) {
        model.addAttribute("user", usersService.findAll());
        return "hf/header";
    }

    @GetMapping("/user/{id}/edit")
    public String getUserEdit(@PathVariable(value = "id") Long id, Model model) {
        Optional<User> user = usersService.findById(id);
        ArrayList<User> res = new ArrayList<>();
        user.ifPresent(res::add);
        model.addAttribute("user", res);
        model.addAttribute("books", bookService.findAll());
        return "user-edit";
    }
    @PostMapping("/user/{id}/edit")
    public String userPostUpdate(@PathVariable(value = "id") Long id, @RequestParam String fullName,
                                 @RequestParam String birth, @RequestParam Role type,@RequestParam String book,
                                 Model model) throws ParseException {
        User user = usersService.findById(id).orElseThrow();
        Date birthDate = dateFormat.get().parse(birth);
        Optional<Book> bookId = bookService.findById(Long.parseLong(book));
        if(bookId.isPresent())
        {
            user.setFullName(fullName);
            user.setBirth(birthDate);
            user.setType(type);
            List<Book> books = user.getBook();
            books.add(bookId.get());
            user.setBook(books);
            usersService.create(user);
        }
        model.addAttribute("user", user);
        model.addAttribute("result", "Success update");
        return "user-details";
    }

    @PostMapping("/user/{id}/remove")
    public String userPostDelete(@PathVariable(value = "id") Long id, Model model) {
        User user = usersService.findById(id).orElseThrow();
        usersService.delete(user.getId());
        return "redirect:/library/user";
    }



}