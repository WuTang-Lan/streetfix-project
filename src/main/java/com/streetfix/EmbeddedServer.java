package com.streetfix;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class EmbeddedServer {
    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(5000);
        tomcat.setHostname("0.0.0.0");

        String webappDir = new File("src/main/webapp").getAbsolutePath();
        tomcat.addWebapp("", webappDir);

        tomcat.getConnector();
        
        System.out.println("Starting StreetFix Nairobi on http://0.0.0.0:5000");
        
        tomcat.start();
        tomcat.getServer().await();
    }
}
