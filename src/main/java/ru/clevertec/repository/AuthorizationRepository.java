package ru.clevertec.repository;

import ru.clevertec.entity.User;
import ru.clevertec.entity.UserRole;

import java.util.List;
import java.util.Optional;

public interface AuthorizationRepository {

    Optional<UserRole> checkUserByLoginAndPassword(List<User> users, String login, String password);
}