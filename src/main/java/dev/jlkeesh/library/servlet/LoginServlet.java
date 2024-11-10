package dev.jlkeesh.library.servlet;

import dev.jlkeesh.library.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.*;

@WebServlet(urlPatterns = {
        "/auth/login"
})
public class LoginServlet extends HttpServlet {
    List<User> users = List.of(
            new User(UUID.randomUUID(), "jl", "123", "uz"),
            new User(UUID.randomUUID(), "jalol", "123", "en_US")
    );

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession(false);
        req.getRequestDispatcher("/views/auth/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        Map<String, String[]> parameterMap = req.getParameterMap();
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String nextUrl = req.getParameter("next_url");
        String rememberMe = Objects.requireNonNullElse(req.getParameter("remember-me"),"off");
        Optional<User> userOptional = users.stream()
                .filter(user -> user.getUsername().equals(username))
                .filter(user -> user.getPassword().equals(password))
                .findFirst();
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            HttpSession session = req.getSession(true);
            session.setAttribute("username", username);
            session.setAttribute("lang", user.getLang());
            session.setAttribute("id", user.getId());
            session.setMaxInactiveInterval((rememberMe.equals("on") ? 86400 : 20));
            System.out.println("nextUrl = " + nextUrl);
            resp.sendRedirect(nextUrl);
        } else {
            req.setAttribute("error_message", "username or password incorrect");
            req.getRequestDispatcher("/views/auth/login.jsp").forward(req, resp);
        }
    }
}
