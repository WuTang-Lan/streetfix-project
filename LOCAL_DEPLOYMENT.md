# 🚀 StreetFix Nairobi - Local Deployment Guide

**Complete guide for deploying StreetFix Nairobi on your local machine**

---

## 📋 **What You Need**

### **Required Software**
1. **IntelliJ IDEA** (Community or Ultimate) - [Download](https://www.jetbrains.com/idea/download/)
2. **JDK 8 or higher** - [Download](https://www.oracle.com/java/technologies/downloads/)
3. **XAMPP** (for MySQL) - [Download](https://www.apachefriends.org/download.html)
4. **Maven** - Bundled with IntelliJ (no separate install needed)

### **System Requirements**
- **OS:** Windows 10/11, macOS, or Linux
- **RAM:** 4GB minimum (8GB recommended)
- **Disk Space:** 500MB for project + dependencies

---

## 🛠️ **Step-by-Step Installation**

### **Step 1: Install XAMPP**

1. Download XAMPP from https://www.apachefriends.org
2. Run the installer
3. Install to default location (e.g., `C:\xampp` on Windows)
4. Open XAMPP Control Panel
5. Click **Start** for **MySQL** (Apache not needed)

✅ **Verify:** MySQL should show "Running" status

---

### **Step 2: Create Database**

1. Open phpMyAdmin: `http://localhost/phpmyadmin`
2. Click "New" in left sidebar
3. Database name: `streetfix_nairobi`
4. Collation: `utf8mb4_general_ci`
5. Click "Create"

6. Select the database
7. Click "Import" tab
8. Choose file: `database_mysql.sql` (from project folder)
9. Click "Go"

✅ **Verify:** You should see 2 tables (users, issues) with sample data

---

### **Step 3: Install IntelliJ IDEA**

1. Download IntelliJ IDEA from https://www.jetbrains.com/idea/download/
2. Choose Community Edition (free) or Ultimate (paid)
3. Run installer and follow prompts
4. Launch IntelliJ IDEA

---

### **Step 4: Open Project in IntelliJ**

1. **File** → **Open**
2. Navigate to project folder: `streetfix-nairobi`
3. Select the folder (should see `pom.xml` inside)
4. Click **OK**
5. Wait for Maven to download dependencies (first time takes 2-5 minutes)
6. Look for "Build successful" in bottom right

✅ **Verify:** Project structure appears in left sidebar

---

### **Step 5: Configure Environment Variables**

1. Click **Run** → **Edit Configurations**
2. Click **+** (Add New Configuration)
3. Select **Application**
4. Configure as follows:

```
Name: StreetFix Nairobi

Main class: com.streetfix.EmbeddedServer
  (Click the folder icon and search for "EmbeddedServer")

Environment variables: (Click "Edit environment variables" icon)
  MYSQL_HOST=localhost
  MYSQL_PORT=3306
  MYSQL_DATABASE=streetfix_nairobi
  MYSQL_USER=root
  MYSQL_PASSWORD=
  (Leave password empty for default XAMPP)

Working directory: (leave default)

Use classpath of module: streetfix-nairobi
```

5. Click **OK**

---

### **Step 6: Run the Application**

1. Click the green **Run** button (or press `Shift + F10`)
2. Watch the console for:
   ```
   Starting StreetFix Nairobi on http://0.0.0.0:5000
   INFO: Starting ProtocolHandler ["http-nio-5000"]
   ```
3. Wait for "Starting ProtocolHandler" message

✅ **Verify:** No errors in console

---

### **Step 7: Test the Application**

1. Open browser
2. Go to: `http://localhost:5000`
3. You should see the beautiful navy blue login page

**Test Login:**
```
Email: john@example.com
Password: password123
```

4. After login, you'll see the dashboard

✅ **Success!** Application is running locally

---

## 🎯 **Quick Reference**

### **Start the Application**
```bash
# From IntelliJ
1. Open the project
2. Click Run button (or Shift+F10)

# From terminal (alternative)
mvn clean compile exec:java
```

### **Stop the Application**
```bash
# From IntelliJ
Click the red Stop button (or Ctrl+F2)
```

### **Rebuild After Changes**
```bash
# From IntelliJ
Build → Rebuild Project (Ctrl+F9)
```

---

## 📁 **File Structure**

After setup, your project should look like this:

```
streetfix-nairobi/
├── .idea/                         # IntelliJ configuration
├── src/
│   └── main/
│       ├── java/com/streetfix/    # Backend code
│       │   ├── dao/               # Database access
│       │   ├── model/             # Data models
│       │   ├── servlet/           # API endpoints
│       │   └── EmbeddedServer.java
│       └── webapp/                # Frontend files
│           ├── index.html         # Login page
│           ├── register.html      # Signup page
│           ├── student-dashboard.html
│           ├── admin-dashboard.html
│           ├── styles.css
│           └── script.js
├── target/
│   └── streetfix.war              # Compiled application
├── database_mysql.sql             # Database schema
├── pom.xml                        # Maven configuration
├── README.md                      # Project overview
├── INTELLIJ_SETUP.md              # Detailed IntelliJ guide
├── XAMPP_SETUP.md                 # XAMPP guide
├── DEPLOYMENT_GUIDE.md            # Deployment options
└── LOCAL_DEPLOYMENT.md            # This file
```

---

## 🧪 **Testing**

### **Test Accounts**

**Admin Account:**
```
Email: admin@streetfix.co.ke
Password: admin123
URL: http://localhost:5000/admin-dashboard.html
```

**Student Account:**
```
Email: john@example.com
Password: password123
URL: http://localhost:5000/student-dashboard.html
```

**Create New Account:**
```
URL: http://localhost:5000/register.html
- Fill in your details
- Password must be 6+ characters
- Submit to create account
- Login with new credentials
```

### **Pages to Test**

1. **Login:** `http://localhost:5000/`
2. **Register:** `http://localhost:5000/register.html`
3. **Student Dashboard:** `http://localhost:5000/student-dashboard.html`
4. **Admin Dashboard:** `http://localhost:5000/admin-dashboard.html`
5. **Report Issue:** `http://localhost:5000/report.html`
6. **Map View:** `http://localhost:5000/map.html`

---

## 🐛 **Troubleshooting**

### **Issue: Port 5000 already in use**
```bash
Error: Address already in use

Solution:
1. Open EmbeddedServer.java
2. Change: tomcat.setPort(5000); to tomcat.setPort(8080);
3. Rebuild and run
4. Access at: http://localhost:8080
```

### **Issue: Database connection failed**
```bash
Error: Could not connect to MySQL

Solution:
1. Open XAMPP Control Panel
2. Check MySQL is running (green highlight)
3. If not, click "Start"
4. Verify database exists in phpMyAdmin
5. Check environment variables are correct
```

### **Issue: Maven dependencies not downloading**
```bash
Error: Cannot resolve dependencies

Solution:
1. Right-click pom.xml
2. Maven → Reload Project
3. Or: mvn clean install -U
4. Wait for download to complete
```

### **Issue: "Cannot find main class"**
```bash
Error: Could not find or load main class

Solution:
1. Run → Edit Configurations
2. Verify Main class: com.streetfix.EmbeddedServer
3. Rebuild project (Ctrl+F9)
4. Try running again
```

### **Issue: Changes not appearing**
```bash
Changes to code not visible in browser

Solution:
1. Stop the application (Ctrl+F2)
2. Rebuild project (Ctrl+F9)
3. Run again (Shift+F10)
4. Hard refresh browser (Ctrl+Shift+R)
```

---

## 🔧 **Development Workflow**

### **Making Code Changes**

1. **Edit Files**
   - Java files: `src/main/java/com/streetfix/`
   - HTML/CSS/JS: `src/main/webapp/`

2. **Rebuild**
   - `Build → Rebuild Project` (Ctrl+F9)

3. **Restart Server**
   - Stop (Ctrl+F2)
   - Run (Shift+F10)

4. **Test Changes**
   - Refresh browser
   - Test functionality

### **Database Changes**

1. **Update Schema**
   - Modify `database_mysql.sql`
   - Drop existing database
   - Re-import SQL file

2. **Update DAO Classes**
   - Edit `UserDAO.java` or `IssueDAO.java`
   - Add new methods
   - Rebuild

---

## 📦 **Building for Distribution**

### **Create WAR File**

```bash
# From terminal
mvn clean package

# Output location:
target/streetfix.war (15MB)
```

### **Run WAR File**

**Option 1: Embedded Server (Current)**
```bash
mvn exec:java
# Runs with embedded Tomcat on port 5000
```

**Option 2: External Tomcat**
```bash
1. Install Tomcat 9.x
2. Copy: target/streetfix.war → tomcat/webapps/
3. Start Tomcat
4. Access: http://localhost:8080/streetfix
```

---

## 🎨 **Customization**

### **Change Port**
```java
// src/main/java/com/streetfix/EmbeddedServer.java
tomcat.setPort(8080); // Change from 5000 to 8080
```

### **Change Colors**
```css
/* src/main/webapp/styles.css */
/* Login page: Navy blue → Your color */
background: linear-gradient(135deg, #YOUR_COLOR_1, #YOUR_COLOR_2);
```

### **Add New Pages**
```
1. Create new HTML file in src/main/webapp/
2. Add link to navigation
3. Create corresponding servlet if needed
4. Rebuild and test
```

---

## 📊 **Performance Tips**

### **Speed Up Development**

1. **Use IntelliJ Debugger**
   - Set breakpoints
   - Run in Debug mode (Shift+F9)
   - Inspect variables

2. **Hot Swap (For small changes)**
   - Make Java changes
   - Build → Build Project
   - No need to restart

3. **Database Tools**
   - IntelliJ: View → Tool Windows → Database
   - Connect to MySQL
   - Browse data visually

---

## ✅ **Deployment Checklist**

Before sharing with others:

- [ ] XAMPP MySQL installed and running
- [ ] Database created and imported
- [ ] IntelliJ project opens without errors
- [ ] Maven dependencies downloaded
- [ ] Environment variables configured
- [ ] Application runs on http://localhost:5000
- [ ] Login page displays correctly
- [ ] Test accounts work
- [ ] Can create new user account
- [ ] Can report issues
- [ ] Dashboard shows statistics
- [ ] No errors in console

---

## 🎓 **Next Steps**

After successful deployment:

1. **Explore the App**
   - Try all features
   - Test both student and admin views
   - Report some test issues

2. **Customize**
   - Change colors to your preference
   - Add your logo
   - Modify text content

3. **Deploy to Production**
   - See DEPLOYMENT_GUIDE.md
   - Consider cloud hosting
   - Set up domain name

---

## 📞 **Getting Help**

**Documentation:**
- [INTELLIJ_SETUP.md](INTELLIJ_SETUP.md) - Detailed IntelliJ guide
- [XAMPP_SETUP.md](XAMPP_SETUP.md) - XAMPP configuration
- [DEPLOYMENT_GUIDE.md](DEPLOYMENT_GUIDE.md) - Deployment options
- [FEATURES.md](FEATURES.md) - Complete feature list

**Common Questions:**
- "How do I change the port?" → See Customization section
- "Database won't connect" → See Troubleshooting section
- "Changes not showing" → Rebuild and restart server

---

## 🎉 **Success!**

You now have StreetFix Nairobi running locally on your machine!

**Access your app:**
- Login: http://localhost:5000
- Register: http://localhost:5000/register.html
- Student Dashboard: http://localhost:5000/student-dashboard.html
- Admin Dashboard: http://localhost:5000/admin-dashboard.html

**Test Account:**
```
Email: john@example.com
Password: password123
```

---

*Happy Coding!* 🚀

---

**StreetFix Nairobi** - Making infrastructure reporting modern, efficient, and beautiful.

*Last Updated: November 20, 2025*
