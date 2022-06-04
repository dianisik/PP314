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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

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

        Role adminRole = new Role ("ROLE_ADMIN");
        Role userRole = new Role ("ROLE_USER");
        roleRepository.save(adminRole);
        roleRepository.save(userRole);

        User admin = new User();
        admin.setName("admin");
        admin.setFirstName("Admin");
        admin.setLastName("Admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setEmail("admin@server.com");
        admin.setRoles(Arrays.asList(adminRole));
        userRepository.save(admin);

        User user = new User();
        user.setName("user");
        user.setFirstName("User");
        user.setLastName("User");
        user.setPassword(passwordEncoder.encode("user"));
        user.setEmail("user@server.com");
        user.setRoles(Arrays.asList(userRole));
        userRepository.save(user);
        alreadySetup = true;
    }

}