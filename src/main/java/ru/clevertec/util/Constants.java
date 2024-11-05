package ru.clevertec.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    public static final String ID = "id";
    public static final String USER_FIRSTNAME = "firstname";
    public static final String USER_SURNAME = "surname";
    public static final String USER_EMAIL = "email";
    public static final String USER_LOGIN = "login";
    public static final String USER_PASSWORD = "password";
    public static final String USER_ROLE = "userRole";
    public static final String USERS = "users";

    public static final String USERS_CREATE = "/users/create";
    public static final String USERS_READ = "/users/read";
    public static final String USERS_UPDATE = "/users/update";
    public static final String USERS_DELETE = "/users/delete";
    public static final String USERS_MENU = "/users/menu";
    public static final String USERS_ALL_PAGES = "/users/*";
    public static final String USERS_CREATE_PAGE = "/pages/create-user.jsp";
    public static final String USERS_READ_PAGE = "/pages/read-users.jsp";
    public static final String USERS_MENU_PAGE = "/pages/users-menu.jsp";
    public static final String USER_MENU_PAGE = "/pages/user-menu.jsp";

    public static final String LOGIN = "/login";
    public static final String REGISTRATION = "/registration";
    public static final String ADMIN_MENU = "/admin-menu";
    public static final String ADMIN_MENU_PAGE = "/pages/admin-menu.jsp";
    public static final String LOGIN_ERROR_PAGE = "/pages/login-error.jsp";
    public static final String ACCESS_ERROR_PAGE = "/pages/access-error.jsp";
    public static final String REGISTRATION_ERROR_PAGE = "/pages/registration-error.jsp";
    public static final String REGISTRATION_PAGE = "/pages/registration.jsp";

    public static final String ID_COLUMN = "ID";
    public static final String PERSON = "PERSON";
    public static final String FIRSTNAME_COLUMN = "FIRSTNAME";
    public static final String SURNAME_COLUMN = "SURNAME";
    public static final String EMAIL_COLUMN = "EMAIL";
    public static final String LOGIN_COLUMN = "LOGIN";
    public static final String PASSWORD_COLUMN = "PASSWORD";
    public static final String ROLE_COLUMN = "ROLE";

    public static final String ACCOUNT = "account";
}