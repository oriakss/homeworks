package ru.clevertec.service;

import ru.clevertec.entity.User;
import ru.clevertec.repository.UserRepository;
import ru.clevertec.repository.UserRepositoryImpl;

import java.util.List;

public class UserServiceImpl implements UserService {

    private static UserService userService;
    private final UserRepository userRepository = UserRepositoryImpl.getInstance();

    @Override
    public void createUser(User user) {
        userRepository.createUser(user).orElseThrow();
    }

    @Override
    public List<User> readUsers() {
        return userRepository.readUsers().orElseThrow();
    }

    @Override
    public void updateUser(User user) {
        userRepository.updateUser(user).orElseThrow();
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteUser(userId).orElseThrow();
    }

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserServiceImpl();
        }
        return userService;
    }

    private UserServiceImpl() {
    }
}