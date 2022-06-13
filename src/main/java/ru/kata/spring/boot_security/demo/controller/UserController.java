package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.MyUserDetailsService;

import java.security.Principal;

@Controller

public class UserController {

    private final
    MyUserDetailsService myUserDetailsService;

    @Autowired
    public UserController(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    @GetMapping("/user")
    public String goHome(Principal principal, Model model){
        User user = myUserDetailsService.findByUserName(principal.getName());
        model.addAttribute("mainUser", user);

        return "user";
    }



}
