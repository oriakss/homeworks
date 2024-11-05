package ru.clevertec.filter;

import ru.clevertec.entity.User;
import ru.clevertec.service.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static ru.clevertec.service.UserServiceImpl.getInstance;
import static ru.clevertec.util.Constants.REGISTRATION;
import static ru.clevertec.util.Constants.REGISTRATION_ERROR_PAGE;
import static ru.clevertec.util.Constants.USERS_CREATE;
import static ru.clevertec.util.Constants.USER_LOGIN;

@WebFilter(urlPatterns = {REGISTRATION, USERS_CREATE})
public class UniqueLoginFilter extends HttpFilter {
    
    UserService userService = getInstance();

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        Optional<User> optionalUser = userService.readUsers()
                .stream()
                .filter(user -> user.getLogin().equals(req.getParameter(USER_LOGIN)))
                .findAny();
        if (optionalUser.isPresent()) {
            req.getRequestDispatcher(REGISTRATION_ERROR_PAGE).forward(req, res);
        } else {
            chain.doFilter(req, res);
        }
    }
}