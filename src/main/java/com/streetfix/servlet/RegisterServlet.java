package com.streetfix.servlet;

import com.streetfix.dao.UserDAO;
import com.streetfix.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegisterServlet extends HttpServlet {
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
            String fullName = request.getParameter("fullName");
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            // Basic validation
            if (fullName == null || email == null || password == null ||
                    fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {

                result.put("success", false);
                result.put("message", "All fields are required");

            } else if (userDAO.emailExists(email)) {
                result.put("success", false);
                result.put("message", "Email already exists");

            } else {
                User user = new User(fullName, email, password);
                boolean registered = userDAO.registerUser(user);

                if (registered) {
                    result.put("success", true);
                    result.put("message", "Registration successful");
                } else {
                    result.put("success", false);
                    result.put("message", "Registration failed");
                }
            }

        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Server error: " + e.getMessage());
        }

        objectMapper.writeValue(response.getWriter(), result);
    }
}