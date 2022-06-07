package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.*;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private final static long ROLE_ADMIN = 0;
    private final static long ROLE_USER = 1;

    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        Iterable<User> users = userRepository.findAll();
        alreadySetup = users.iterator().hasNext(); //если пользователи уже есть, ничего делать не надо

        Role adminRole = new Role (ROLE_ADMIN, "Администратор");
        Role userRole = new Role (ROLE_USER, "Пользователь");
        Set<Role> adminRoles = Collections.emptySet();
        adminRoles.add(adminRole);
        Set<Role> userRoles = Collections.emptySet();
        userRoles.add(userRole);
        roleRepository.save(adminRole);
        roleRepository.save(userRole);

        User admin = new User();
        admin.setName("admin");
        admin.setFirstName("Admin");
        admin.setLastName("Admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setEmail("admin@server.com");
        admin.setRoles(adminRoles);
        userRepository.save(admin);

        User user = new User();
        user.setName("user");
        user.setFirstName("User");
        user.setLastName("User");
        user.setPassword(passwordEncoder.encode("user"));
        user.setEmail("user@server.com");
        user.setRoles(userRoles);
        userRepository.save(user);
        alreadySetup = true;
    }

}