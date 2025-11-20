# Setting Up StreetFix Nairobi in IntelliJ IDEA

This guide will walk you through setting up the StreetFix Nairobi project in IntelliJ IDEA and connecting it to XAMPP's MySQL database.

## Prerequisites

### Required Software
1. **IntelliJ IDEA** (Community or Ultimate Edition)
   - Download from: https://www.jetbrains.com/idea/download/
   
2. **JDK 8 or Higher**
   - Download from: https://www.oracle.com/java/technologies/downloads/
   
3. **Apache Maven 3.6+**
   - Download from: https://maven.apache.org/download.cgi
   - Or use IntelliJ's bundled Maven
   
4. **XAMPP**
   - Download from: https://www.apachefriends.org/download.html

---

## Part 1: Setting Up XAMPP

### Step 1: Install and Start XAMPP

1. Install XAMPP on your computer
2. Open **XAMPP Control Panel**
3. Start **Apache** and **MySQL** services
4. Verify both services show green "Running" status

### Step 2: Create MySQL Database

#### Using phpMyAdmin (Recommended):
1. Open browser and go to: `http://localhost/phpmyadmin`
2. Click **"New"** in left sidebar
3. Database name: `streetfix_nairobi`
4. Collation: `utf8mb4_general_ci`
5. Click **"Create"**

#### Import Database Schema:
1. Click on `streetfix_nairobi` database
2. Go to **"SQL"** tab
3. Copy entire content from `database_mysql.sql` file in project root
4. Paste into SQL editor
5. Click **"Go"** to execute

You should now see:
- `users` table with 3 sample users
- `issues` table with 3 sample issues

---

## Part 2: Setting Up IntelliJ IDEA

### Step 1: Import Project

1. **Open IntelliJ IDEA**
2. Click **"Open"** or **"File" → "Open"**
3. Navigate to your project folder (where `pom.xml` is located)
4. Select the **folder** (not pom.xml file)
5. Click **"OK"**
6. IntelliJ will detect it as a Maven project and import dependencies

### Step 2: Configure JDK

1. Go to **"File" → "Project Structure"** (or press `Ctrl+Alt+Shift+S`)
2. Under **"Project"**:
   - Set **Project SDK** to Java 8 or higher
   - Set **Project language level** to 8
3. Click **"Apply"** → **"OK"**

### Step 3: Configure Maven

1. IntelliJ usually auto-detects Maven
2. If not, go to **"File" → "Settings" → "Build, Execution, Deployment" → "Build Tools" → "Maven"**
3. Ensure Maven home is set correctly
4. Click **"Apply"** → **"OK"**

### Step 4: Set Environment Variables

#### Option A: Run Configuration (Recommended)
1. Go to **"Run" → "Edit Configurations"**
2. Click **"+"** → **"Application"**
3. Configuration settings:
   - **Name**: `StreetFix Nairobi`
   - **Main class**: `com.streetfix.EmbeddedServer`
   - **Working directory**: `$PROJECT_DIR$`
   - **Use classpath of module**: `streetfix-nairobi`
   
4. **Environment Variables**: Click the folder icon and add:
   ```
   MYSQL_HOST=localhost
   MYSQL_PORT=3306
   MYSQL_DATABASE=streetfix_nairobi
   MYSQL_USER=root
   MYSQL_PASSWORD=
   ```

5. Click **"Apply"** → **"OK"**

#### Option B: System Environment Variables
1. **Windows**:
   - Right-click **"This PC"** → **"Properties"**
   - Click **"Advanced system settings"**
   - Click **"Environment Variables"**
   - Under **"User variables"**, click **"New"** for each:
     - Variable: `MYSQL_HOST`, Value: `localhost`
     - Variable: `MYSQL_PORT`, Value: `3306`
     - Variable: `MYSQL_DATABASE`, Value: `streetfix_nairobi`
     - Variable: `MYSQL_USER`, Value: `root`
     - Variable: `MYSQL_PASSWORD`, Value: (leave empty)

2. **Mac/Linux**:
   - Edit `.bashrc` or `.zshrc`:
     ```bash
     export MYSQL_HOST=localhost
     export MYSQL_PORT=3306
     export MYSQL_DATABASE=streetfix_nairobi
     export MYSQL_USER=root
     export MYSQL_PASSWORD=
     ```
   - Restart IntelliJ after setting

### Step 5: Build the Project

1. Open **Maven** panel (View → Tool Windows → Maven)
2. Expand **Lifecycle**
3. Double-click **"clean"**
4. Double-click **"compile"**
5. Wait for build to complete (watch bottom status bar)

---

## Part 3: Running the Application

### Method 1: Using Run Configuration

1. Select **"StreetFix Nairobi"** configuration from dropdown (top-right)
2. Click **green "Run"** button or press `Shift+F10`
3. Watch console output

### Method 2: Using Maven

1. Open **Maven** panel
2. Expand **Plugins** → **exec**
3. Double-click **"exec:java"**

### Method 3: Using Terminal

1. Open **Terminal** in IntelliJ (Alt+F12)
2. Run:
   ```bash
   mvn clean compile exec:java
   ```

### Verify Successful Startup

You should see in console:
```
MySQL connection configured: localhost:3306/streetfix_nairobi
Database configured: MySQL
Starting StreetFix Nairobi on http://0.0.0.0:5000
INFO: Starting ProtocolHandler ["http-nio-5000"]
```

---

## Part 4: Accessing the Application

1. **Open browser** and navigate to:
   ```
   http://localhost:5000
   ```

2. **Test Login** with sample accounts:
   - **Admin User**:
     - Email: `admin@streetfix.co.ke`
     - Password: `admin123`
   
   - **Student User**:
     - Email: `john@example.com`
     - Password: `password123`

3. **Explore Dashboards**:
   - Student Dashboard: `http://localhost:5000/student-dashboard.html`
   - Admin Dashboard: `http://localhost:5000/admin-dashboard.html`
   - Report Issue: `http://localhost:5000/report.html`
   - View Map: `http://localhost:5000/map.html`

---

## Part 5: Development Workflow

### Hot Reload / Auto-Restart

IntelliJ IDEA doesn't have automatic hot reload for Maven projects by default. To see changes:

1. **Stop** the running application (Red square button)
2. Make your code changes
3. **Run** again

### Debugging

1. Click **green bug icon** instead of Run (or press `Shift+F9`)
2. Set breakpoints by clicking left margin of code
3. Use **Debug** panel to step through code

### Viewing Database

#### Option 1: IntelliJ Database Tool
1. Open **Database** panel (View → Tool Windows → Database)
2. Click **"+"** → **"Data Source"** → **"MySQL"**
3. Configuration:
   - Host: `localhost`
   - Port: `3306`
   - Database: `streetfix_nairobi`
   - User: `root`
   - Password: (leave empty)
4. Click **"Test Connection"** → **"OK"**
5. Now you can browse tables, run queries, etc.

#### Option 2: phpMyAdmin
1. Open: `http://localhost/phpmyadmin`
2. Select `streetfix_nairobi` database
3. Browse tables

---

## Troubleshooting

### Issue: "Cannot resolve symbol 'Class'"
**Solution**: 
- Right-click project → **"Maven" → "Reload Project"**
- Or: **"File" → "Invalidate Caches / Restart"**

### Issue: "Port 5000 already in use"
**Solution**:
- Stop any other applications using port 5000
- Or change port in `EmbeddedServer.java`:
  ```java
  private static final int PORT = 8080; // Change to any available port
  ```

### Issue: "Cannot connect to MySQL"
**Solution**:
- Verify XAMPP MySQL is running
- Check environment variables are set correctly
- Verify database `streetfix_nairobi` exists
- Try `localhost` vs `127.0.0.1`

### Issue: "ClassNotFoundException: com.mysql.cj.jdbc.Driver"
**Solution**:
- Maven dependencies not downloaded
- Run: **"Maven" → "Reimport"**
- Or: **"Maven" → "Download Sources and Documentation"**

### Issue: Build fails with compilation errors
**Solution**:
- Ensure JDK 8+ is configured
- Check **"File" → "Project Structure" → "Project SDK"**
- Run **"Maven" → "Reimport All Maven Projects"**

---

## Project Structure in IntelliJ

```
streetfix-nairobi/
├── src/
│   └── main/
│       ├── java/com/streetfix/
│       │   ├── dao/              # Database Access
│       │   │   ├── DatabaseConnection.java
│       │   │   ├── UserDAO.java
│       │   │   └── IssueDAO.java
│       │   ├── model/            # Data Models
│       │   │   ├── User.java
│       │   │   └── Issue.java
│       │   ├── servlet/          # API Endpoints
│       │   │   ├── LoginServlet.java
│       │   │   ├── RegisterServlet.java
│       │   │   ├── IssueServlet.java
│       │   │   └── DashboardServlet.java
│       │   └── EmbeddedServer.java  # Main entry point
│       └── webapp/               # Frontend Files
│           ├── index.html        # Login page
│           ├── register.html     # Registration
│           ├── student-dashboard.html
│           ├── admin-dashboard.html
│           ├── report.html       # Report new issue
│           ├── map.html          # Issues map
│           ├── styles.css        # All styles
│           └── script.js         # Client-side logic
├── pom.xml                       # Maven config
├── database_mysql.sql            # MySQL schema
├── XAMPP_SETUP.md               # XAMPP guide
└── INTELLIJ_SETUP.md            # This file
```

---

## Tips for Development

### 1. Code Navigation
- **Ctrl+N**: Find class
- **Ctrl+Shift+N**: Find file
- **Ctrl+Click**: Go to definition
- **Alt+F7**: Find usages

### 2. Running Specific Classes
- Right-click any class with `main()` method
- Select **"Run 'ClassName.main()'"**

### 3. Maven Commands
- **clean**: Delete target folder
- **compile**: Compile source code
- **package**: Create WAR file
- **exec:java**: Run main class

### 4. Git Integration
- IntelliJ has built-in Git support
- **VCS → Commit** to commit changes
- **VCS → Update Project** to pull changes

---

## Next Steps

1. ✅ Verify application runs successfully
2. ✅ Test login with sample users
3. ✅ Explore student and admin dashboards
4. ✅ Try reporting a new issue
5. ✅ View issues on the map
6. 🎯 Start customizing and adding features!

---

## Need Help?

- Check **console logs** in IntelliJ for errors
- Verify **MySQL connection** in phpMyAdmin
- Review **XAMPP_SETUP.md** for database issues
- Check **Environment Variables** are set correctly

Happy coding! 🚀
