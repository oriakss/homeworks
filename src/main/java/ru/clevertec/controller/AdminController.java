package ru.clevertec.controller;

import ru.clevertec.service.UserService;
import ru.clevertec.service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.clevertec.entity.UserRole.ADMIN;
import static ru.clevertec.util.Constants.ADMIN_MENU;
import static ru.clevertec.util.Constants.ADMIN_MENU_PAGE;
import static ru.clevertec.util.Constants.USERS;
import static ru.clevertec.util.Constants.USERS_MENU;
import static ru.clevertec.util.Constants.USERS_MENU_PAGE;
import static ru.clevertec.util.Constants.USER_ROLE;

@WebServlet(urlPatterns = {ADMIN_MENU, USERS_MENU})
public class AdminController extends HttpServlet {

    private final UserService userService = UserServiceImpl.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (req.getRequestURI()) {
            case ADMIN_MENU -> openAdminMenu(req, resp);
            case USERS_MENU -> openUsersMenu(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private void openUsersMenu(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(USERS, userService.readUsers());
        req.getRequestDispatcher(USERS_MENU_PAGE).forward(req, resp);
    }

    private static void openAdminMenu(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute(USER_ROLE, ADMIN);
        req.getRequestDispatcher(ADMIN_MENU_PAGE).forward(req, resp);
    }
}