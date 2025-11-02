package com.streetfix.servlet;

import com.streetfix.dao.IssueDAO;
import com.streetfix.model.Issue;
import com.streetfix.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class IssueServlet extends HttpServlet {
    private IssueDAO issueDAO;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        issueDAO = new IssueDAO();
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
                String title = request.getParameter("title");
                String description = request.getParameter("description");
                String issueType = request.getParameter("issueType");
                String location = request.getParameter("location");

                // Get latitude and longitude if available
                Double latitude = null;
                Double longitude = null;
                String latStr = request.getParameter("latitude");
                String lngStr = request.getParameter("longitude");

                if (latStr != null && !latStr.isEmpty()) {
                    latitude = Double.parseDouble(latStr);
                }
                if (lngStr != null && !lngStr.isEmpty()) {
                    longitude = Double.parseDouble(lngStr);
                }

                Issue issue = new Issue(user.getId(), title, description, issueType, location);
                issue.setLatitude(latitude);
                issue.setLongitude(longitude);

                boolean created = issueDAO.createIssue(issue);

                if (created) {
                    result.put("success", true);
                    result.put("message", "Issue reported successfully");
                } else {
                    result.put("success", false);
                    result.put("message", "Failed to report issue");
                }
            }

        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Server error: " + e.getMessage());
        }

        objectMapper.writeValue(response.getWriter(), result);
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
                String type = request.getParameter("type");

                if ("user".equals(type)) {
                    // Get user's issues
                    result.put("success", true);
                    result.put("issues", issueDAO.getUserIssues(user.getId()));
                } else {
                    // Get all issues
                    result.put("success", true);
                    result.put("issues", issueDAO.getAllIssues());
                }
            }

        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Server error: " + e.getMessage());
        }

        objectMapper.writeValue(response.getWriter(), result);
    }
}