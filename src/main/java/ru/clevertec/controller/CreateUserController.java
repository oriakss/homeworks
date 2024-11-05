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
import static ru.clevertec.util.Constants.USERS_CREATE;
import static ru.clevertec.util.Constants.USERS_CREATE_PAGE;
import static ru.clevertec.util.Constants.USERS_MENU;

@WebServlet(urlPatterns = USERS_CREATE)
public class CreateUserController extends HttpServlet {

    private final UserService userService = getInstance();
    private final UserMapper userMapper = UserMapper.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(USERS_CREATE_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userService.createUser(userMapper.buildUser(req));
        req.getRequestDispatcher(USERS_MENU).forward(req, resp);
    }
}