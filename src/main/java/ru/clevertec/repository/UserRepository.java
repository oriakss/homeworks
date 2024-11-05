package ru.clevertec.repository;

import ru.clevertec.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> createUser(User user);

    Optional<List<User>> readUsers();

    Optional<User> updateUser(User user);

    Optional<User> deleteUser(Long userId);
}