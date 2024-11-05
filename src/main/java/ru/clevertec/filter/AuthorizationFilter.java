package ru.clevertec.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.clevertec.entity.UserRole.ADMIN;
import static ru.clevertec.util.Constants.ACCESS_ERROR_PAGE;
import static ru.clevertec.util.Constants.ADMIN_MENU;
import static ru.clevertec.util.Constants.USERS_ALL_PAGES;
import static ru.clevertec.util.Constants.USER_ROLE;

@WebFilter(urlPatterns = {ADMIN_MENU, USERS_ALL_PAGES})
public class AuthorizationFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        Object userRole = req.getSession().getAttribute(USER_ROLE);
        if (userRole == ADMIN) {
            chain.doFilter(req, res);
        } else {
            req.getRequestDispatcher(ACCESS_ERROR_PAGE).forward(req, res);
        }
    }
}