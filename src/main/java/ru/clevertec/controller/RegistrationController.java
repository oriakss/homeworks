package ru.clevertec.controller;

import ru.clevertec.mapper.UserMapper;
import ru.clevertec.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.clevertec.service.UserServiceImpl.getInstance;
import static ru.clevertec.util.Constants.REGISTRATION;
import static ru.clevertec.util.Constants.REGISTRATION_PAGE;
import static ru.clevertec.util.Constants.USER_MENU_PAGE;
import static ru.clevertec.util.Constants.USER_ROLE;

@WebServlet(urlPatterns = REGISTRATION)
public class RegistrationController extends HttpServlet {

    private final UserService userService = getInstance();
    private final UserMapper userMapper = UserMapper.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(REGISTRATION_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userService.createUser(userMapper.buildUser(req));
        req.getSession().setAttribute(USER_ROLE, req.getParameter(USER_ROLE));
        req.getRequestDispatcher(USER_MENU_PAGE).forward(req, resp);
    }
}