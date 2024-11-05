package ru.clevertec.controller;

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
import static ru.clevertec.util.Constants.USERS_DELETE;
import static ru.clevertec.util.Constants.USERS_MENU;

@WebServlet(urlPatterns = USERS_DELETE)
public class DeleteUserController extends HttpServlet {

    private final UserService userService = getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userService.deleteUser(valueOf(req.getParameter(ID)));
        req.getRequestDispatcher(USERS_MENU).forward(req, resp);
    }
}