package ru.kata.spring.boot_security.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import ru.kata.spring.boot_security.demo.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByName(String lastName);
}
