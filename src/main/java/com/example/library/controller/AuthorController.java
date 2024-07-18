package com.example.library.controller;

import com.example.library.dto.AuthorDto;
import com.example.library.dto.BookDto;
import com.example.library.dto.PageRequestDto;
import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.repository.AuthorRepository;
import com.example.library.service.AuthorService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;


@Controller
@RequestMapping("/library")
public class  AuthorController {
    private static final ThreadLocal<SimpleDateFormat> dateFormat =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("dd.MM.yyyy"));
    @Autowired
    AuthorService authorService;
    @Autowired
    private AuthorRepository authorRepository;


    @GetMapping("/author")
    public String getAllAuthors(Model model, String keyword,
                                @RequestParam(value="pageSize", defaultValue = "1", required = false) int pageSize,
                                @RequestParam(value="pageNo", defaultValue = "0", required = false) int pageNo) {



        if(keyword != null) {
            model.addAttribute("authors", authorService.findByKeyword(keyword));
        }
        else {
//            model.addAttribute("pageNo", pageNo);

            model.addAttribute("authors", authorService.findAll(pageNo, pageSize));
        }

        Page<Author> pageMax = authorService.findAll(pageNo, pageSize);

        model.addAttribute("pageNo", pageNo);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageNoMax", pageMax.getTotalPages());
        return "author-main";
    }

//    @PostMapping("/author")
//    public String postAllAuthors(Model model) {
//        pageNo++;
//        model.addAttribute("pageNo", pageNo);
//        return "redirect:/library/author";
//    }

    @GetMapping("/author/{id}")
    public String getAuthorById(@PathVariable(value = "id") Long id, Model model) {
       Optional<Author> author = authorService.findById(id);
        ArrayList<Author> authors = new ArrayList<>();
        author.ifPresent(authors::add);
        model.addAttribute("author", authors);
        return "author-details";
    }


    @GetMapping("/author/{id}/edit")
    public String getAuthorEdit(@PathVariable(value = "id") Long id, Model model) {
        AuthorDto authorDto = new AuthorDto();
        Optional<Author> authorOptional = authorService.findById(id);
        if (authorOptional.isPresent()){
            Author author = authorOptional.get();
            authorDto.setGuid(author.getGuid());
            authorDto.setBirth(dateFormat.get().format(author.getBirth()));
            authorDto.setFullName(author.getFullName());
            authorDto.setBiography(author.getBiography());
        }
        model.addAttribute("author", authorDto);
        return "author-edit";
    }

    @PostMapping("/author/edit")
    public String authorPostAdd(@Valid @ModelAttribute("author") AuthorDto authorDto, BindingResult result, Model model)
            throws ParseException {
        if (result.hasErrors()){
            model.addAttribute("author", authorDto);
            return "author-edit";
        }
        authorService.update(authorDto);
        return "redirect:/library/author";
    }

    @PostMapping("/author/{id}/remove")
    public String authorPostDelete(@PathVariable(value = "id") Long id) {
        Author author = authorService.findById(id).orElseThrow();
        authorService.delete(author.getId());
        return "redirect:/library/author";
    }

}
