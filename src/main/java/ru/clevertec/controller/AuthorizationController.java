package ru.clevertec.controller;

import ru.clevertec.entity.UserRole;
import ru.clevertec.service.AuthorizationService;
import ru.clevertec.service.AuthorizationServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.clevertec.util.Constants.ADMIN_MENU;
import static ru.clevertec.util.Constants.LOGIN;
import static ru.clevertec.util.Constants.LOGIN_ERROR_PAGE;
import static ru.clevertec.util.Constants.USER_LOGIN;
import static ru.clevertec.util.Constants.USER_MENU_PAGE;
import static ru.clevertec.util.Constants.USER_PASSWORD;
import static ru.clevertec.util.Constants.USER_ROLE;

@WebServlet(urlPatterns = LOGIN)
public class AuthorizationController extends HttpServlet {

    private final AuthorizationService authorizationService = AuthorizationServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserRole userRole = authorizationService
                .checkUserByLoginAndPassword(req.getParameter(USER_LOGIN), req.getParameter(USER_PASSWORD));
        switch (userRole) {
            case ADMIN -> openAdminMenu(req, resp, userRole);
            case USER -> openUserMenu(req, resp, userRole);
            default -> req.getRequestDispatcher(LOGIN_ERROR_PAGE).forward(req, resp);
        }
    }

    private static void openUserMenu(HttpServletRequest req, HttpServletResponse resp, UserRole userRole) throws ServletException, IOException {
        req.getSession().setAttribute(USER_ROLE, userRole);
        req.getRequestDispatcher(USER_MENU_PAGE).forward(req, resp);
    }

    private static void openAdminMenu(HttpServletRequest req, HttpServletResponse resp, UserRole userRole) throws ServletException, IOException {
        req.getSession().setAttribute(USER_ROLE, userRole);
        req.getRequestDispatcher(ADMIN_MENU).forward(req, resp);
    }
}