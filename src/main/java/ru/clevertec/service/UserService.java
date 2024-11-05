package ru.clevertec.service;

import ru.clevertec.entity.User;

import java.util.List;

public interface UserService {

    void createUser(User user);

    List<User> readUsers();

    void updateUser(User user);

    void deleteUser(Long userId);
}