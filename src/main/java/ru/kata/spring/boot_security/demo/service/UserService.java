package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll ();
    User findUserById(long id);
    void saveAndFlush(User user);
    void deleteById (long id);
    void save (User user);


    User findUserByEmail(String name);

    List<User> getAllUsers();

    void saveOrUpdate(User user);

    void delete(Long id);
}
