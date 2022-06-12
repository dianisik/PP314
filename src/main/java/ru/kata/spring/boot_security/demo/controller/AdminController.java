package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.MyUserDetailsService;

import java.util.ArrayList;
import java.util.Set;

@Controller
public class AdminController {

     private final MyUserDetailsService myUserDetailsService;

     private final PasswordEncoder passwordEncoder;

    @Autowired
    AdminController(MyUserDetailsService myUserDetailsService, PasswordEncoder passwordEncoder) {
        this.myUserDetailsService = myUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "admin/users")
    public String listUsers(Model model) {
        model.addAttribute("allUsers", myUserDetailsService.findAll());
        return "users";
    }
    @GetMapping(value = "/edit/{id}")
    public String editUser(@PathVariable long id, Model model) {
        model.addAttribute("user", myUserDetailsService.findUserById(id));
        return "/edit";
    }

    @PostMapping(value = "/admin")
    public String updateUser(@ModelAttribute User user , @RequestParam(value = "role") ArrayList<Long> roles) {
        Set<Role> roleArrayList = myUserDetailsService.getRoles(roles);
        user.setRoles(roleArrayList);
        user.setPassword(passwordEncoder.encode(user.getPassword())); //шифруем пароль
        myUserDetailsService.saveAndFlush(user);
        return "redirect:/admin/users";
    }
    @GetMapping(value = "/remove/{id}")
    public String removeUser(@PathVariable long id) {
        myUserDetailsService.deleteById (id);
        return "redirect:/admin/users";
    }
    @GetMapping ("/admin")
    public String newUser(){
        return "admin";
    }
    @PostMapping("/user")
    public RedirectView create(@ModelAttribute User user, @RequestParam(value = "role") ArrayList <Long> roles, Model model) {
        Set<Role> roleArrayList = myUserDetailsService.getRoles(roles);
        user.setRoles(roleArrayList);
        user.setPassword(passwordEncoder.encode(user.getPassword())); //шифруем пароль
        myUserDetailsService.save(user);
        return new RedirectView("/admin");
    }

}
