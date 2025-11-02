package com.streetfix.servlet;

import com.streetfix.dao.UserDAO;
import com.streetfix.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends HttpServlet {
    private UserDAO userDAO;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> result = new HashMap<>();

        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            User user = userDAO.loginUser(email, password);

            if (user != null) {
                // Store user in session
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setMaxInactiveInterval(30 * 60); // 30 minutes

                result.put("success", true);
                result.put("message", "Login successful");
                result.put("user", user);
            } else {
                result.put("success", false);
                result.put("message", "Invalid email or password");
            }

        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Server error: " + e.getMessage());
        }

        objectMapper.writeValue(response.getWriter(), result);
    }
}