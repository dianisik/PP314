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

import java.security.Principal;
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

    @GetMapping(value = "/admin")
    public String listUsers(Model model, Principal principal) {
        model.addAttribute("allUsers", myUserDetailsService.findAll());
        User user = myUserDetailsService.findByUserName(principal.getName());
        model.addAttribute("mainUser",user);
        return "admin";
    }
    @GetMapping(value = "/admin/{id}")
    public String editUser(@PathVariable long id, Model model) {
        model.addAttribute("user", myUserDetailsService.findUserById(id));
        return "redirect:/admin/";
    }

    @PostMapping(value = "/admin")
    public String updateUser(@ModelAttribute User user , @RequestParam(value = "role") ArrayList<Long> roles) {

        Set<Role> roleArrayList = myUserDetailsService.getRoles(roles);
        user.setRoles(roleArrayList);
        user.setPassword(passwordEncoder.encode(user.getPassword())); //шифруем пароль

        myUserDetailsService.saveAndFlush(user);
        return "redirect:/admin";
    }
    @PostMapping(value = "/admin/{id}")
    public String removeUser(@PathVariable long id) {
        myUserDetailsService.deleteById (id);
        return "redirect:/admin/";
    }
    @GetMapping ("/new")
    public String newUser(Model model,Principal principal){
        model.addAttribute("newUser",new User());
        model.addAttribute("role",new ArrayList<Role>());
        User user = myUserDetailsService.findByUserName(principal.getName());
        model.addAttribute("mainUser",user);
        return "new";
    }
    @PostMapping("/new")
    public RedirectView create(@ModelAttribute User user, @RequestParam(value = "role") ArrayList <Long> roles, Model model) {
        Set<Role> roleArrayList = myUserDetailsService.getRoles(roles);
        user.setRoles(roleArrayList);
        user.setPassword(passwordEncoder.encode(user.getPassword())); //шифруем пароль
        myUserDetailsService.save(user);
        return new RedirectView("/admin");
    }

    @GetMapping("/admin_info")
    public String goHome(Principal principal, Model model){
        User user = myUserDetailsService.findByUserName(principal.getName());
        model.addAttribute("mainUser", user);

        return "admin_info";
    }
}
