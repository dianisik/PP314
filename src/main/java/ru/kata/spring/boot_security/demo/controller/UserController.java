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

import java.util.ArrayList;
import java.util.Arrays;

@Controller
@RequestMapping("/user")

public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    MyUserDetailsService myUserDetailsService;

    @GetMapping
    public String goHome(){
        return "user-space";
    }



    @PreAuthorize("hasAuthority('ROLE_ADMIN')") //только пользователь с ролью админа может выполнять запросы
    @PostMapping
    public RedirectView create(@ModelAttribute User user, @RequestParam(value = "role") String[]roles, BindingResult errors, Model model) {
        ArrayList<Role> roleArrayList = myUserDetailsService.getRoleCollectionToStringArray(roles);
        roleRepository.saveAll(roleArrayList);
        user.setRoles(roleArrayList);
        user.setPassword(passwordEncoder.encode(user.getPassword())); //шифруем пароль
        userRepository.save(user);
        return new RedirectView("/admin");
    }
}
