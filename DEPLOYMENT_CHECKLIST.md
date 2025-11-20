# ✅ StreetFix Nairobi - Deployment Checklist

**Use this checklist to ensure successful local deployment**

---

## 📋 **Pre-Deployment Checklist**

### **1. Software Installation**

- [ ] JDK 8 or higher installed
  ```bash
  # Verify: java -version
  # Should show: java version "1.8.0" or higher
  ```

- [ ] IntelliJ IDEA installed
  ```bash
  # Download from: https://www.jetbrains.com/idea/download/
  # Community Edition (free) is sufficient
  ```

- [ ] XAMPP installed
  ```bash
  # Download from: https://www.apachefriends.org
  # Version: Latest stable release
  ```

- [ ] Maven available
  ```bash
  # Verify: mvn -version
  # Usually bundled with IntelliJ (no separate install needed)
  ```

---

## 🗄️ **Database Setup**

### **2. MySQL Configuration**

- [ ] XAMPP MySQL service started
  ```bash
  # XAMPP Control Panel → MySQL → Start
  # Should show "Running" in green
  ```

- [ ] phpMyAdmin accessible
  ```bash
  # Open: http://localhost/phpmyadmin
  # Should load without errors
  ```

- [ ] Database created
  ```sql
  # Database name: streetfix_nairobi
  # Collation: utf8mb4_general_ci
  ```

- [ ] SQL schema imported
  ```bash
  # Import file: database_mysql.sql
  # Should see: 2 tables (users, issues)
  # Should see: 3+ rows of sample data
  ```

- [ ] Test connection
  ```bash
  # In phpMyAdmin, click on "users" table
  # Should see 3 test users including admin
  ```

---

## 💻 **Project Setup**

### **3. IntelliJ Configuration**

- [ ] Project opened in IntelliJ
  ```bash
  # File → Open → streetfix-nairobi folder
  # Should see pom.xml recognized
  ```

- [ ] Maven dependencies downloaded
  ```bash
  # Bottom right: "Build successful"
  # Check: External Libraries in Project view
  # Should see: servlet-api, postgresql, mysql-connector, etc.
  ```

- [ ] No compilation errors
  ```bash
  # Build → Rebuild Project
  # Should complete without errors
  ```

- [ ] Run configuration created
  ```
  Name: StreetFix Nairobi
  Main class: com.streetfix.EmbeddedServer
  Environment variables set (see next section)
  ```

---

## ⚙️ **Environment Configuration**

### **4. Environment Variables**

- [ ] `MYSQL_HOST` set to `localhost`
- [ ] `MYSQL_PORT` set to `3306`
- [ ] `MYSQL_DATABASE` set to `streetfix_nairobi`
- [ ] `MYSQL_USER` set to `root`
- [ ] `MYSQL_PASSWORD` set (empty for default XAMPP)

**Verify Configuration:**
```
Run → Edit Configurations → Environment variables
Should show all 5 variables correctly set
```

---

## 🚀 **Application Launch**

### **5. First Run**

- [ ] Application starts without errors
  ```bash
  # Click Run (Shift+F10)
  # Console should show: "Starting ProtocolHandler"
  # No red error messages
  ```

- [ ] Port 5000 is accessible
  ```bash
  # Console shows: http://0.0.0.0:5000
  # Port not in use by another application
  ```

- [ ] Server logs look healthy
  ```bash
  # Should see:
  INFO: Starting service [Tomcat]
  INFO: Starting Servlet engine
  INFO: Starting ProtocolHandler ["http-nio-5000"]
  ```

---

## 🌐 **Web Interface Testing**

### **6. Frontend Access**

- [ ] Login page loads
  ```bash
  # Open: http://localhost:5000
  # Should see navy blue gradient background
  # Form elements visible and styled
  ```

- [ ] CSS and JavaScript loaded
  ```bash
  # No "404 Not Found" errors in browser console
  # Animations and hover effects working
  ```

- [ ] Images and icons display
  ```bash
  # Check browser console (F12)
  # No missing resource errors
  ```

---

## 🔐 **Authentication Testing**

### **7. Login Functionality**

- [ ] Test login with admin account
  ```
  Email: admin@streetfix.co.ke
  Password: admin123
  Should redirect to dashboard
  ```

- [ ] Test login with student account
  ```
  Email: john@example.com
  Password: password123
  Should redirect to dashboard
  ```

- [ ] Invalid credentials rejected
  ```
  Email: wrong@email.com
  Password: wrongpass
  Should show error message
  ```

- [ ] Session management working
  ```bash
  # After login, refresh page
  # Should remain logged in
  ```

---

## 📝 **Registration Testing**

### **8. User Registration**

- [ ] Registration page loads
  ```bash
  # URL: http://localhost:5000/register.html
  # Form displays correctly
  ```

- [ ] Password strength meter works
  ```bash
  # Type password in field
  # Meter shows: Weak/Medium/Strong
  ```

- [ ] Can create new account
  ```
  Full Name: Test User
  Email: test@example.com
  Password: testpass123
  Should create account successfully
  ```

- [ ] Can login with new account
  ```
  Use credentials from previous step
  Should login successfully
  ```

- [ ] Duplicate email rejected
  ```
  Try registering with existing email
  Should show error message
  ```

---

## 📊 **Dashboard Testing**

### **9. Student Dashboard**

- [ ] Dashboard loads
  ```bash
  # URL: http://localhost:5000/student-dashboard.html
  # After login as student
  ```

- [ ] Action cards display
  ```bash
  # Should see: Report New Issue, Track Your Reports
  # Cards should be large and prominent
  # Hover effects working
  ```

- [ ] Statistics show correctly
  ```bash
  # Should display: Total reports, Pending, Resolved
  # Numbers match database data
  ```

---

### **10. Admin Dashboard**

- [ ] Admin dashboard loads
  ```bash
  # URL: http://localhost:5000/admin-dashboard.html
  # After login as admin
  ```

- [ ] Admin action cards display
  ```bash
  # Should see: Manage All Reports, Track All Issues
  # Different from student dashboard
  ```

- [ ] Admin statistics correct
  ```bash
  # Total Reports, Awaiting Review, In Progress, Resolved
  # Shows all users' data, not just admin's
  ```

---

## 🗺️ **Feature Testing**

### **11. Issue Reporting**

- [ ] Report page loads
  ```bash
  # URL: http://localhost:5000/report.html
  ```

- [ ] Form fields work
  ```bash
  # Can enter: Title, Description, Type, Location
  # All fields accept input
  ```

- [ ] Can submit issue
  ```bash
  # Fill out form and submit
  # Should see success message
  ```

- [ ] Issue appears in database
  ```bash
  # Check phpMyAdmin → issues table
  # New row added with correct data
  ```

---

### **12. Map Functionality**

- [ ] Map page loads
  ```bash
  # URL: http://localhost:5000/map.html
  ```

- [ ] Map displays
  ```bash
  # Should show Nairobi area
  # No loading errors
  ```

- [ ] Issues marked on map
  ```bash
  # Markers for reported issues
  # Clickable for details
  ```

---

## 🐛 **Error Testing**

### **13. Error Handling**

- [ ] Database disconnection handled
  ```bash
  # Stop MySQL in XAMPP
  # App should show connection error, not crash
  ```

- [ ] Invalid routes handled
  ```bash
  # Visit: http://localhost:5000/nonexistent
  # Should show 404 or redirect gracefully
  ```

- [ ] Session timeout handled
  ```bash
  # Wait 30+ minutes
  # Refresh page → should redirect to login
  ```

---

## 📦 **Build Testing**

### **14. WAR File Generation**

- [ ] Can build WAR file
  ```bash
  # Terminal: mvn clean package
  # Should complete without errors
  ```

- [ ] WAR file created
  ```bash
  # Check: target/streetfix.war
  # File size: ~15MB
  ```

- [ ] WAR file contains all resources
  ```bash
  # Extract and verify:
  # - All servlets
  # - All HTML files
  # - CSS and JavaScript
  # - WEB-INF/web.xml (if present)
  ```

---

## 🎨 **UI/UX Testing**

### **15. Visual Verification**

- [ ] Color theme consistent
  ```bash
  # Login: Navy blue (#0f172a, #1e293b)
  # Register: Teal (#134e4a, #14b8a6)
  # Buttons: Cyan (#0ea5e9, #06b6d4)
  ```

- [ ] Responsive design works
  ```bash
  # Resize browser window
  # Layout adjusts properly
  # No broken elements
  ```

- [ ] Hover effects smooth
  ```bash
  # Hover over cards and buttons
  # Transitions are smooth
  # Colors change as expected
  ```

- [ ] Forms well-aligned
  ```bash
  # All input fields aligned
  # Labels properly positioned
  # Submit buttons centered
  ```

---

## 📚 **Documentation Check**

### **16. Documentation Complete**

- [ ] README.md present and accurate
- [ ] LOCAL_DEPLOYMENT.md present
- [ ] INTELLIJ_SETUP.md present
- [ ] XAMPP_SETUP.md present
- [ ] DEPLOYMENT_GUIDE.md present
- [ ] FEATURES.md present
- [ ] database_mysql.sql present
- [ ] This checklist (DEPLOYMENT_CHECKLIST.md) present

---

## 🔒 **Security Check**

### **17. Security Basics**

- [ ] No passwords in source code
- [ ] Environment variables used for config
- [ ] SQL injection protection (PreparedStatements used)
- [ ] Session management implemented
- [ ] Input validation in place

**⚠️ Production Note:**
```
For production deployment, add:
- Password hashing (BCrypt)
- HTTPS/SSL certificates
- Rate limiting
- CSRF protection
- Input sanitization
```

---

## ✅ **Final Verification**

### **18. Complete System Test**

**Full User Journey:**
1. [ ] Open application → See login page
2. [ ] Click Register → Create new account
3. [ ] Login with new account → See dashboard
4. [ ] Click Report Issue → Fill form → Submit
5. [ ] View dashboard → See new issue count
6. [ ] Click Map → See issue marker
7. [ ] Logout → Return to login page
8. [ ] Login as admin → See all issues

**All tests passing?** ✅ **Deployment successful!**

---

## 📊 **Performance Benchmarks**

### **19. Performance Targets**

- [ ] Login response < 1 second
- [ ] Dashboard load < 2 seconds
- [ ] Issue submission < 1.5 seconds
- [ ] Map rendering < 3 seconds
- [ ] No memory leaks (check after 30 min usage)

---

## 🚨 **Common Issues Resolved**

### **20. Troubleshooting Completed**

If you encountered any issues, verify they're resolved:

- [ ] Port conflicts → Changed port or freed port 5000
- [ ] Database connection → XAMPP MySQL running
- [ ] Maven errors → Dependencies downloaded successfully
- [ ] Compilation errors → Code compiles without errors
- [ ] 404 errors → Static resources accessible
- [ ] Session issues → Cookies working properly

---

## 🎉 **Deployment Complete!**

If all checkboxes above are checked ✅, your deployment is **SUCCESSFUL**!

### **Next Steps:**

1. **Use the Application**
   - Create real user accounts
   - Report actual issues
   - Test all features thoroughly

2. **Customize**
   - Change branding/colors if needed
   - Add your logo
   - Modify text content

3. **Deploy to Production** (Optional)
   - Choose hosting provider
   - Set up domain name
   - Configure SSL certificate
   - See DEPLOYMENT_GUIDE.md

---

## 📞 **Support**

**Need Help?**

1. Check error messages in:
   - IntelliJ console
   - Browser console (F12)
   - phpMyAdmin

2. Consult documentation:
   - LOCAL_DEPLOYMENT.md
   - INTELLIJ_SETUP.md
   - XAMPP_SETUP.md

3. Review troubleshooting sections in each guide

---

## 📋 **Quick Command Reference**

```bash
# Start application
mvn clean compile exec:java

# Build WAR file
mvn clean package

# Rebuild in IntelliJ
Ctrl+F9 (Windows/Linux)
Cmd+F9 (Mac)

# Run in IntelliJ
Shift+F10 (Windows/Linux)
Ctrl+R (Mac)

# Stop application
Ctrl+F2 (Windows/Linux)
Cmd+F2 (Mac)
```

---

**Congratulations! 🎊**

StreetFix Nairobi is now successfully deployed on your local machine!

---

*Checklist Version: 1.0*  
*Last Updated: November 20, 2025*
