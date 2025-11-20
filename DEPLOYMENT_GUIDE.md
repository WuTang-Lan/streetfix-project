# StreetFix Nairobi - IntelliJ IDEA Deployment Guide

## 📦 Package Contents

This package contains everything you need to deploy StreetFix Nairobi locally using IntelliJ IDEA and XAMPP.

### Project Files
```
streetfix-nairobi/
├── src/                          # Source code
│   └── main/
│       ├── java/                 # Backend Java code
│       └── webapp/               # Frontend files
├── target/                       # Compiled files
│   └── streetfix.war            # Deployable WAR file
├── database_mysql.sql           # MySQL database schema
├── pom.xml                      # Maven configuration
├── INTELLIJ_SETUP.md           # Complete IntelliJ setup guide
├── XAMPP_SETUP.md              # XAMPP installation guide
├── FEATURES.md                 # Feature documentation
└── DEPLOYMENT_GUIDE.md         # This file
```

---

## 🚀 Quick Start (5 Steps)

### Step 1: Install Prerequisites

**Required Software:**
1. **IntelliJ IDEA** - https://www.jetbrains.com/idea/download/
2. **JDK 8+** - https://www.oracle.com/java/technologies/downloads/
3. **XAMPP** - https://www.apachefriends.org/download.html
4. **Maven** (bundled with IntelliJ)

### Step 2: Setup XAMPP Database

1. Install and start XAMPP
2. Start **Apache** and **MySQL** services
3. Open phpMyAdmin: `http://localhost/phpmyadmin`
4. Create database: `streetfix_nairobi`
5. Import schema: Copy content from `database_mysql.sql` → SQL tab → Execute

### Step 3: Import Project in IntelliJ

1. Open IntelliJ IDEA
2. **File → Open** → Select project folder
3. IntelliJ auto-detects Maven project
4. Wait for dependencies to download (bottom-right progress bar)

### Step 4: Configure Environment Variables

**Method 1: Run Configuration (Recommended)**

1. **Run → Edit Configurations**
2. Click **"+"** → **Application**
3. Settings:
   - **Name**: `StreetFix Nairobi`
   - **Main class**: `com.streetfix.EmbeddedServer`
   - **Working directory**: `$PROJECT_DIR$`
   - **Module**: `streetfix-nairobi`

4. **Environment Variables** (click folder icon):
   ```
   MYSQL_HOST=localhost
   MYSQL_PORT=3306
   MYSQL_DATABASE=streetfix_nairobi
   MYSQL_USER=root
   MYSQL_PASSWORD=
   ```
   *(Leave MYSQL_PASSWORD empty for default XAMPP)*

5. Click **Apply** → **OK**

**Method 2: System Variables (Alternative)**

Windows:
```
Right-click "This PC" → Properties → Advanced Settings → Environment Variables
Add each variable under "User variables"
```

Mac/Linux:
```bash
# Add to ~/.bashrc or ~/.zshrc
export MYSQL_HOST=localhost
export MYSQL_PORT=3306
export MYSQL_DATABASE=streetfix_nairobi
export MYSQL_USER=root
export MYSQL_PASSWORD=
```

### Step 5: Run the Application

1. Select **"StreetFix Nairobi"** from dropdown (top-right)
2. Click green **Run** button (or press `Shift+F10`)
3. Wait for console message: `Starting ProtocolHandler ["http-nio-5000"]`
4. Open browser: **http://localhost:5000**

---

## ✅ Verification Checklist

### Before Running
- [ ] XAMPP MySQL service is running (green in control panel)
- [ ] Database `streetfix_nairobi` exists
- [ ] Tables `users` and `issues` are created with sample data
- [ ] JDK 8+ is configured in IntelliJ
- [ ] Environment variables are set
- [ ] Maven dependencies are downloaded

### After Running
- [ ] Console shows: `Starting StreetFix Nairobi on http://0.0.0.0:5000`
- [ ] No error messages in console
- [ ] Browser opens to login page
- [ ] Login page has purple gradient background
- [ ] Sample login works: `admin@streetfix.co.ke` / `admin123`

---

## 🧪 Testing the Application

### Test Accounts (Pre-configured)

**Admin Account:**
- Email: `admin@streetfix.co.ke`
- Password: `admin123`
- Access: Admin Dashboard

**Student Account 1:**
- Email: `john@example.com`
- Password: `password123`
- Access: Student Dashboard

**Student Account 2:**
- Email: `jane@example.com`
- Password: `password456`
- Access: Student Dashboard

### What to Test

1. **Login Page** (`/`)
   - Modern purple gradient design
   - Email/password validation
   - "Create account" link works

2. **Registration** (`/register.html`)
   - Green gradient background
   - Password strength meter
   - Form validation
   - Account creation

3. **Student Dashboard** (`/student-dashboard.html`)
   - Personal statistics
   - Report New Issue card
   - Track Reports card
   - Notification center

4. **Admin Dashboard** (`/admin-dashboard.html`)
   - Community statistics
   - Manage All Reports
   - Performance metrics
   - Admin tools

5. **Report Issue** (`/report.html`)
   - Issue form submission
   - Location selection
   - Photo upload

6. **Map View** (`/map.html`)
   - Issues displayed on map
   - Marker click details
   - Filter by type/status

---

## 🔧 Development Workflow

### Building the Project

```bash
# Clean and compile
mvn clean compile

# Create WAR file
mvn clean package

# Run application
mvn clean compile exec:java
```

### Maven Lifecycle (in IntelliJ)

1. Open **Maven** panel (View → Tool Windows → Maven)
2. Expand **Lifecycle**:
   - **clean** - Delete build artifacts
   - **compile** - Compile source code
   - **package** - Create WAR file
   - **install** - Install to local Maven repo

### Running Different Configurations

**Option 1: Direct Execution**
- Right-click `EmbeddedServer.java`
- Select **"Run 'EmbeddedServer.main()'"**

**Option 2: Maven Exec**
- Maven panel → Plugins → exec → `exec:java`

**Option 3: Run Configuration**
- Use the configured "StreetFix Nairobi" run config

### Debugging

1. Click **debug icon** (green bug) instead of Run
2. Set breakpoints: Click left margin of code line
3. Use Debug panel:
   - **Step Over** (F8)
   - **Step Into** (F7)
   - **Resume** (F9)

---

## 🗄️ Database Management

### Using phpMyAdmin

1. Open: `http://localhost/phpmyadmin`
2. Select `streetfix_nairobi` database
3. Browse tables, run queries, export data

### Using IntelliJ Database Tool

1. **View → Tool Windows → Database**
2. Click **"+"** → **Data Source** → **MySQL**
3. Configure:
   - Host: `localhost`
   - Port: `3306`
   - Database: `streetfix_nairobi`
   - User: `root`
   - Password: *(empty)*
4. **Test Connection** → **OK**
5. Now you can:
   - Browse tables visually
   - Run SQL queries
   - Export/import data
   - View relationships

### Database Schema

**Users Table:**
```sql
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

**Issues Table:**
```sql
CREATE TABLE issues (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    issue_type VARCHAR(100),
    location VARCHAR(255),
    latitude DOUBLE,
    longitude DOUBLE,
    status VARCHAR(50) DEFAULT 'pending',
    photo_path VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

---

## 🛠️ Troubleshooting

### Issue: "Cannot connect to database"

**Solutions:**
1. Verify XAMPP MySQL is running
2. Check database `streetfix_nairobi` exists
3. Verify environment variables are set correctly
4. Test connection in phpMyAdmin
5. Check firewall isn't blocking port 3306

**Console should show:**
```
MySQL connection configured: localhost:3306/streetfix_nairobi
Database configured: MySQL
```

### Issue: "Port 5000 already in use"

**Solutions:**
1. Stop other applications using port 5000
2. Change port in `EmbeddedServer.java`:
   ```java
   private static final int PORT = 8080; // Any available port
   ```
3. Update your browser URL accordingly

### Issue: "ClassNotFoundException: com.mysql.cj.jdbc.Driver"

**Solutions:**
1. Maven dependencies not downloaded
2. IntelliJ: **Maven → Reload All Maven Projects**
3. Or: **File → Invalidate Caches / Restart**

### Issue: "BUILD FAILURE" when compiling

**Solutions:**
1. Check JDK version: **File → Project Structure → Project SDK**
2. Ensure JDK 8+ is selected
3. Clean Maven: `mvn clean`
4. Reimport project: **Maven → Reimport**

### Issue: Login page looks broken (no styles)

**Solutions:**
1. Check console for 404 errors on CSS/JS files
2. Verify `styles.css` and `script.js` exist in `src/main/webapp/`
3. Clear browser cache (Ctrl+Shift+Delete)
4. Hard refresh (Ctrl+F5)

### Issue: Password strength meter not working

**Solution:**
- Check browser console (F12) for JavaScript errors
- Ensure the password field ID matches: `id="password"`
- Script is at bottom of `register.html`

---

## 📂 Project Structure Explained

```
src/main/java/com/streetfix/
├── dao/                          # Data Access Objects
│   ├── DatabaseConnection.java  # DB connection manager
│   ├── UserDAO.java             # User database operations
│   └── IssueDAO.java            # Issue database operations
│
├── model/                        # Data Models
│   ├── User.java                # User entity
│   └── Issue.java               # Issue entity
│
├── servlet/                      # HTTP Endpoints
│   ├── LoginServlet.java        # POST /login
│   ├── RegisterServlet.java     # POST /register
│   ├── IssueServlet.java        # GET/POST /issues
│   └── DashboardServlet.java    # GET /dashboard
│
└── EmbeddedServer.java          # Main application (Tomcat)

src/main/webapp/                  # Frontend Files
├── index.html                   # Login page
├── register.html                # Signup page
├── student-dashboard.html       # Student portal
├── admin-dashboard.html         # Admin portal
├── report.html                  # Report issue form
├── map.html                     # Issues map
├── styles.css                   # All CSS styles
└── script.js                    # Client-side JavaScript
```

---

## 🔐 Security Notes

### For Development
- Default XAMPP credentials (root, no password) are OK
- Database is only accessible on localhost

### For Production
**NEVER deploy to production with these settings:**

1. **Change MySQL password:**
   ```sql
   ALTER USER 'root'@'localhost' IDENTIFIED BY 'strong_password_here';
   ```

2. **Create dedicated database user:**
   ```sql
   CREATE USER 'streetfix_user'@'localhost' IDENTIFIED BY 'secure_password';
   GRANT ALL PRIVILEGES ON streetfix_nairobi.* TO 'streetfix_user'@'localhost';
   FLUSH PRIVILEGES;
   ```

3. **Enable password hashing** (already implemented in code)

4. **Use HTTPS** for secure connections

5. **Set secure session management**

---

## 🚢 Deployment Options

### Option 1: Embedded Server (Current)
- Runs Tomcat inside the application
- Good for: Development, testing
- Command: `mvn compile exec:java`

### Option 2: WAR Deployment
- Deploy to external Tomcat/Jetty
- Good for: Production environments
- File: `target/streetfix.war`
- Deploy to: Tomcat webapps folder

### Option 3: Cloud Deployment (Replit)
- Uses PostgreSQL instead of MySQL
- Automatic when deployed to Replit
- No configuration needed (auto-detects)

---

## 📊 Performance Tips

### Database Optimization
1. Index frequently queried columns
2. Use connection pooling (already configured)
3. Regular backup schedule

### Application Optimization
1. Enable caching for static resources
2. Compress CSS/JS files
3. Optimize image sizes

### IntelliJ Tips
1. Increase memory: **Help → Edit Custom VM Options**
2. Disable unused plugins
3. Use "Power Save Mode" for better battery life

---

## 📞 Support & Resources

### Documentation Files
- `INTELLIJ_SETUP.md` - Detailed IntelliJ setup
- `XAMPP_SETUP.md` - XAMPP configuration
- `FEATURES.md` - Complete feature list
- `database_mysql.sql` - Database schema

### Useful Links
- IntelliJ IDEA Docs: https://www.jetbrains.com/help/idea/
- Maven Guide: https://maven.apache.org/guides/
- XAMPP Forums: https://community.apachefriends.org/

### Console Messages

**Successful Startup:**
```
MySQL connection configured: localhost:3306/streetfix_nairobi
Database configured: MySQL
Starting StreetFix Nairobi on http://0.0.0.0:5000
INFO: Starting ProtocolHandler ["http-nio-5000"]
```

**Connection Error:**
```
Error connecting to database: Connection refused
```
→ Check XAMPP MySQL is running

---

## ✅ Production Readiness Checklist

Before deploying to production:

- [ ] Change default database credentials
- [ ] Enable HTTPS/SSL
- [ ] Configure proper error pages
- [ ] Set up logging and monitoring
- [ ] Enable database backups
- [ ] Review and update security settings
- [ ] Test all features thoroughly
- [ ] Set up email notifications
- [ ] Configure production environment variables
- [ ] Review and optimize performance

---

## 🎯 Next Steps

1. ✅ Complete IntelliJ setup
2. ✅ Test all features with sample data
3. ✅ Customize branding and content
4. ✅ Add real user accounts
5. ✅ Start reporting real issues!

---

**Happy Coding!** 🚀

For detailed setup instructions, see `INTELLIJ_SETUP.md`
For features and capabilities, see `FEATURES.md`
