package ru.clevertec.controller;

import ru.clevertec.entity.User;
import ru.clevertec.mapper.UserMapper;
import ru.clevertec.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.lang.Long.valueOf;
import static ru.clevertec.service.UserServiceImpl.getInstance;
import static ru.clevertec.util.Constants.ID;
import static ru.clevertec.util.Constants.USERS_MENU;
import static ru.clevertec.util.Constants.USERS_UPDATE;

@WebServlet(urlPatterns = USERS_UPDATE)
public class UpdateUserController extends HttpServlet {

    private final UserService userService = getInstance();
    private final UserMapper userMapper = UserMapper.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = userMapper.buildUser(req);
        user.setId(valueOf(req.getParameter(ID)));
        userService.updateUser(user);
        req.getRequestDispatcher(USERS_MENU).forward(req, resp);
    }
}