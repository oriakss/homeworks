package ru.clevertec.service;

import ru.clevertec.entity.UserRole;

public interface AuthorizationService {

    UserRole checkUserByLoginAndPassword(String login, String password);
}