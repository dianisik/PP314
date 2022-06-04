package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

@Controller
public class AdminController {

    @Autowired
    UserRepository userRepository;

    @GetMapping(value = "admin/users")
    public String listUsers(Model model) {
        model.addAttribute("allUsers", userRepository.findAll());
        return "users";
    }
}
