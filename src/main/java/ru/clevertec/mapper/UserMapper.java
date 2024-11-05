package ru.clevertec.mapper;

import ru.clevertec.entity.User;
import ru.clevertec.entity.UserRole;

import javax.servlet.http.HttpServletRequest;

import static ru.clevertec.entity.UserRole.valueOf;
import static ru.clevertec.util.Constants.USER_EMAIL;
import static ru.clevertec.util.Constants.USER_FIRSTNAME;
import static ru.clevertec.util.Constants.USER_LOGIN;
import static ru.clevertec.util.Constants.USER_PASSWORD;
import static ru.clevertec.util.Constants.USER_ROLE;
import static ru.clevertec.util.Constants.USER_SURNAME;

public class UserMapper {

    private static UserMapper userMapper;

    public User buildUser(HttpServletRequest req) {
        return User.builder()
                .firstname(req.getParameter(USER_FIRSTNAME))
                .surname(req.getParameter(USER_SURNAME))
                .email(req.getParameter(USER_EMAIL))
                .login(req.getParameter(USER_LOGIN))
                .password(req.getParameter(USER_PASSWORD))
                .userRole(valueOf(req.getParameter(USER_ROLE)))
                .build();
    }

    public User buildUserManually(String firstname, String surname, String email, String login, String password, UserRole userRole) {
        return User.builder()
                .firstname(firstname)
                .surname(surname)
                .email(email)
                .login(login)
                .password(password)
                .userRole(userRole)
                .build();
    }

    public static UserMapper getInstance() {
        if (userMapper == null) {
            userMapper = new UserMapper();
        }
        return userMapper;
    }

    private UserMapper() {
    }
}