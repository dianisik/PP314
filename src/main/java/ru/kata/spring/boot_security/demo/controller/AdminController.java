package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.MyUserDetailsService;

import java.util.ArrayList;

@Controller
public class AdminController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @GetMapping(value = "admin/users")
    public String listUsers(Model model) {
        model.addAttribute("allUsers", userRepository.findAll());
        return "users";
    }
    @GetMapping(value = "/edit/{id}")
    public String editUser(@PathVariable long id, Model model) {
        model.addAttribute("user", userRepository.findUserById(id));
        return "/edit";
    }

    @PostMapping(value = "/userUpdate")
    public String updateUser(@ModelAttribute User user , @RequestParam(value = "role") String[]roles, @PathVariable ("id") Long id) {
        ArrayList<Role> roleArrayList = myUserDetailsService.getRoleCollectionToStringArray(roles);
        roleRepository.saveAll(roleArrayList);
        user.setRoles(roleArrayList);
        user.setPassword(passwordEncoder.encode(user.getPassword())); //шифруем пароль
        userRepository.saveAndFlush(user);
        return "redirect:/users";
    }

}
