package dev.jlkeesh.library.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;

@WebFilter(urlPatterns = {
        "/*"
})
public class AuthFilter extends HttpFilter {

    public static final String[] WHITE_LIST = {
            "/auth/login",
            "/auth/logout"
    };

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String requestURI = req.getRequestURI();
        if (isInWhiteList(requestURI)) {
            chain.doFilter(req, res);
            return;
        }
        HttpSession session = req.getSession(false);
        if (session == null) {
            res.sendRedirect(req.getContextPath() + "/auth/login?next_url=" + requestURI);
            return;
        }
        chain.doFilter(req, res);
    }

    public boolean isInWhiteList(String requestURI) {
        for (String uri : WHITE_LIST) {
            if (requestURI.equals(uri)) {
                return true;
            }
        }
        return false;
    }
}
