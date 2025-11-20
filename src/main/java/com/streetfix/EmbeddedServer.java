package com.streetfix;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.servlets.DefaultServlet;
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

        Tomcat.addServlet(context, "default", new DefaultServlet());
        context.addServletMappingDecoded("/", "default");

        Tomcat.addServlet(context, "RegisterServlet", new RegisterServlet());
        context.addServletMappingDecoded("/register", "RegisterServlet");

        Tomcat.addServlet(context, "LoginServlet", new LoginServlet());
        context.addServletMappingDecoded("/login", "LoginServlet");

        Tomcat.addServlet(context, "IssueServlet", new IssueServlet());
        context.addServletMappingDecoded("/issues", "IssueServlet");

        Tomcat.addServlet(context, "DashboardServlet", new DashboardServlet());
        context.addServletMappingDecoded("/dashboard", "DashboardServlet");

        context.addWelcomeFile("index.html");

        tomcat.getConnector();
        
        System.out.println("Starting StreetFix Nairobi on http://0.0.0.0:5000");
        
        tomcat.start();
        tomcat.getServer().await();
    }
}
