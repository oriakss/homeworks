package ru.clevertec.service;

import ru.clevertec.entity.UserRole;
import ru.clevertec.repository.AuthorizationRepository;
import ru.clevertec.repository.AuthorizationRepositoryImpl;

import static ru.clevertec.entity.UserRole.NO_ROLE;

public class AuthorizationServiceImpl implements AuthorizationService {

    private static AuthorizationService authorizationService;
    private final AuthorizationRepository authorizationRepository = AuthorizationRepositoryImpl.getInstance();
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public UserRole checkUserByLoginAndPassword(String login, String password) {
        return authorizationRepository
                .checkUserByLoginAndPassword(userService.readUsers(), login, password)
                .orElse(NO_ROLE);
    }

    public static AuthorizationService getInstance() {
        if (authorizationService == null) {
            authorizationService = new AuthorizationServiceImpl();
        }
        return authorizationService;
    }

    private AuthorizationServiceImpl() {
    }
}