package com.example.library.controller;

import com.example.library.model.Author;
import com.example.library.model.User;
import com.example.library.model.enums.Role;
import com.example.library.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static com.example.library.model.enums.Role.READER;


@Controller
public class MainController {
    private static final ThreadLocal<SimpleDateFormat> dateFormat =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("dd.MM.yyyy"));
    @Autowired
    UsersService usersService;
    @Autowired
    private PasswordEncoder passwordEncoder;

        @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Главная странница");
        return "home";
    }

    @GetMapping("/registration")
    public String registration() {
            return "registration";
    }

    @PostMapping("/registration")
    public String registrationPost(Map<String, Object> model,
                                   @RequestParam String full_name, @RequestParam String birth,
                                   @RequestParam String password, @RequestParam String username)
            throws ParseException {
        Date birthDate = dateFormat.get().parse(birth);

        User user = new User(full_name, birthDate, true, passwordEncoder.encode(password), READER, username);
        usersService.create(user);
        return "redirect:/login";
    }
}
