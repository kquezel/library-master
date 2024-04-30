package com.example.library.controller;

import com.example.library.model.Author;
import com.example.library.service.AuthorService;
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
public class  AuthorController {
    private static final ThreadLocal<SimpleDateFormat> dateFormat =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("dd.MM.yyyy"));
    @Autowired
    AuthorService authorService;

    @GetMapping("/author")
    public String getAllAuthors(Model model) {
        List<Author> authors = authorService.findAll();
        model.addAttribute("authors", authors);
        return "author-main";
    }

//    @GetMapping("/author/{id}")
//    public ResponseEntity<Optional<Author>> getAuthorById(@PathVariable(value = "id") Long authorId)
//            throws ResourceNotFoundException {
//        Optional<Author> authors = authorService.findById(authorId);
//        return ResponseEntity.ok().body(authors);
//    }
    @GetMapping("/author/{id}")
    public String getAuthorById(@PathVariable(value = "id") Long authorId, Model model) {
       Optional<Author> author = authorService.findById(authorId);
        ArrayList<Author> res = new ArrayList<>();
        author.ifPresent(res::add);
        model.addAttribute("author", res);
        return "author-details";
    }

    @GetMapping("/author/add")
    public String authorAdd(Model model) {
        return "author-add";
    }
    @PostMapping("/author/add")
    public String authorPostAdd(@RequestParam String fullName, @RequestParam String birth,@RequestParam String biography,
                                Model model) throws ParseException {
        Date birthDate = dateFormat.get().parse(birth);
        Author author = new Author(fullName, birthDate, biography);
        authorService.create(author);
        return "redirect:/library/author";
    }

    @GetMapping("/author/{id}/edit")
    public String getAuthorEdit(@PathVariable(value = "id") Long authorId, Model model) {
        Optional<Author> author = authorService.findById(authorId);
        ArrayList<Author> res = new ArrayList<>();
        author.ifPresent(res::add);
        model.addAttribute("author", res);
        return "author-edit";
    }
    @PostMapping("/author/{id}/edit")
    public String authorPostUpdate(@PathVariable(value = "id") Long authorId, @RequestParam String fullName,
                                   @RequestParam String birth, @RequestParam String biography, Model model)
            throws ParseException {
        Author author = authorService.findById(authorId).orElseThrow();
        Date birthDate = dateFormat.get().parse(birth);
        author.setFullName(fullName);
        author.setBirth(birthDate);
        author.setBiography(biography);
        authorService.create(author);
        model.addAttribute("author", author);
        model.addAttribute("result", "Success update");
        return "author-details";
    }

    @PostMapping("/author/{id}/remove")
    public String authorPostDelete(@PathVariable(value = "id") Long authorId, Model model) {
        Author author = authorService.findById(authorId).orElseThrow();
        authorService.delete(author.getAuthorId());
        return "redirect:/library/author";
    }



}
