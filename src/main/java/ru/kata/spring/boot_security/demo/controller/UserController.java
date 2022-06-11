package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.MyUserDetailsService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;

@Controller
@RequestMapping("/user")

public class UserController {

    private final
    MyUserDetailsService myUserDetailsService;

    @Autowired
    public UserController(MyUserDetailsService myUserDetailsService) {
       //s.userRepository = userRepository;
        //this.passwordEncoder = passwordEncoder;
        //this.roleRepository = roleRepository;
        this.myUserDetailsService = myUserDetailsService;
    }

    @GetMapping
    public String goHome(Principal principal, Model model){
        User user = myUserDetailsService.findByUserName(principal.getName());
        model.addAttribute("user", user);
        return "users";
    }



}
