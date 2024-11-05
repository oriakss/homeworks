package ru.clevertec.repository;

import ru.clevertec.entity.User;
import ru.clevertec.entity.UserRole;

import java.util.List;
import java.util.Optional;

public class AuthorizationRepositoryImpl implements AuthorizationRepository {

    private static AuthorizationRepository authorizationRepository;

    @Override
    public Optional<UserRole> checkUserByLoginAndPassword(List<User> users, String login, String password) {
        return users.stream()
                .filter(user -> user.getLogin().equals(login) && user.getPassword().equals(password))
                .map(User::getUserRole)
                .findAny();
    }

    public static AuthorizationRepository getInstance() {
        if (authorizationRepository == null) {
            authorizationRepository = new AuthorizationRepositoryImpl();
        }
        return authorizationRepository;
    }

    private AuthorizationRepositoryImpl() {
    }
}