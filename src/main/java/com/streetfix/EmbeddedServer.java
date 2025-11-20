package com.streetfix;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import com.streetfix.servlet.RegisterServlet;
import com.streetfix.servlet.LoginServlet;
import com.streetfix.servlet.IssueServlet;
import com.streetfix.servlet.DashboardServlet;

import java.io.File;

public class EmbeddedServer {
    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(5000);
        tomcat.setHostname("0.0.0.0");

        String webappDir = new File("src/main/webapp").getAbsolutePath();
        Context context = tomcat.addContext("", webappDir);

        tomcat.addServlet("", "RegisterServlet", new RegisterServlet());
        context.addServletMappingDecoded("/register", "RegisterServlet");

        tomcat.addServlet("", "LoginServlet", new LoginServlet());
        context.addServletMappingDecoded("/login", "LoginServlet");

        tomcat.addServlet("", "IssueServlet", new IssueServlet());
        context.addServletMappingDecoded("/issues", "IssueServlet");

        tomcat.addServlet("", "DashboardServlet", new DashboardServlet());
        context.addServletMappingDecoded("/dashboard", "DashboardServlet");

        tomcat.getConnector();
        
        System.out.println("Starting StreetFix Nairobi on http://0.0.0.0:5000");
        
        tomcat.start();
        tomcat.getServer().await();
    }
}
