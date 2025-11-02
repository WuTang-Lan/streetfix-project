package com.streetfix.servlet;

import com.streetfix.dao.IssueDAO;
import com.streetfix.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DashboardServlet extends HttpServlet {
    private IssueDAO issueDAO;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        issueDAO = new IssueDAO();
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> result = new HashMap<>();

        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");

            if (user == null) {
                result.put("success", false);
                result.put("message", "Please login first");
            } else {
                // Get dashboard statistics
                int total = issueDAO.getTotalIssues();
                int pending = issueDAO.getIssuesCountByStatus("pending");
                int inProgress = issueDAO.getIssuesCountByStatus("inprogress");
                int resolved = issueDAO.getIssuesCountByStatus("resolved");
                int userReports = issueDAO.getUserIssues(user.getId()).size();

                Map<String, Integer> stats = new HashMap<>();
                stats.put("total", total);
                stats.put("pending", pending);
                stats.put("inProgress", inProgress);
                stats.put("resolved", resolved);
                stats.put("userReports", userReports);

                result.put("success", true);
                result.put("stats", stats);
                result.put("user", user);
            }

        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Server error: " + e.getMessage());
        }

        objectMapper.writeValue(response.getWriter(), result);
    }
}