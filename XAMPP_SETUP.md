# Setting Up StreetFix Nairobi with XAMPP

## Prerequisites
- XAMPP installed on your computer
- Java 8 or higher installed
- Maven installed

## Step 1: Start XAMPP Services

1. Open XAMPP Control Panel
2. Start **Apache** and **MySQL** services
3. Ensure both services are running (green indicators)

## Step 2: Create MySQL Database

### Option A: Using phpMyAdmin (Recommended)
1. Open your browser and go to `http://localhost/phpmyadmin`
2. Click on "SQL" tab
3. Copy and paste the contents of `database_mysql.sql` file
4. Click "Go" to execute the script
5. You should see the `streetfix_nairobi` database created with sample data

### Option B: Using MySQL Command Line
1. Open Command Prompt/Terminal
2. Navigate to MySQL bin directory (e.g., `C:\xampp\mysql\bin`)
3. Run: `mysql -u root -p < path/to/database_mysql.sql`
4. Press Enter (default XAMPP MySQL has no password)

## Step 3: Configure Environment Variables

### For Windows:
1. Open System Properties → Advanced → Environment Variables
2. Add these System Variables:
   - `MYSQL_HOST` = `localhost`
   - `MYSQL_PORT` = `3306`
   - `MYSQL_DATABASE` = `streetfix_nairobi`
   - `MYSQL_USER` = `root`
   - `MYSQL_PASSWORD` = `` (leave empty for XAMPP default)

### For Mac/Linux:
Add to your `.bashrc` or `.zshrc`:
```bash
export MYSQL_HOST=localhost
export MYSQL_PORT=3306
export MYSQL_DATABASE=streetfix_nairobi
export MYSQL_USER=root
export MYSQL_PASSWORD=
```

## Step 4: Build and Run the Application

1. Open Command Prompt/Terminal in the project directory
2. Build the project:
   ```bash
   mvn clean compile
   ```

3. Run the application:
   ```bash
   mvn exec:java
   ```

4. The application will start on `http://localhost:5000`

## Step 5: Test the Application

1. Open browser and navigate to `http://localhost:5000`
2. Try logging in with test credentials:
   - **Email**: `admin@streetfix.co.ke`
   - **Password**: `admin123`
   
   OR
   
   - **Email**: `john@example.com`
   - **Password**: `password123`

3. You should see the dashboard with your reports!

## Verification

When the application starts, you should see in the console:
```
MySQL connection configured: localhost:3306/streetfix_nairobi
Database configured: MySQL
Starting StreetFix Nairobi on http://0.0.0.0:5000
```

## Troubleshooting

### Connection Refused
- Make sure MySQL service is running in XAMPP Control Panel
- Verify port 3306 is not blocked by firewall

### Database Not Found
- Check if database was created successfully in phpMyAdmin
- Run the `database_mysql.sql` script again

### Authentication Failed
- XAMPP's default MySQL user is `root` with no password
- If you've changed the password, update the `MYSQL_PASSWORD` environment variable

### Application Won't Start
- Verify Java and Maven are installed: `java -version` and `mvn -version`
- Ensure port 5000 is not being used by another application

## Default XAMPP MySQL Settings

- **Host**: localhost
- **Port**: 3306
- **Username**: root
- **Password**: (empty)
- **Database**: streetfix_nairobi

## Switching Between MySQL (XAMPP) and PostgreSQL (Replit)

The application automatically detects which database to use:
- **If `MYSQL_HOST` environment variable is set** → Uses MySQL
- **If `DATABASE_URL` or `PGHOST` is set** → Uses PostgreSQL

To switch from MySQL to PostgreSQL, simply remove the `MYSQL_HOST` environment variable.

## Next Steps

- Access Student Dashboard: `http://localhost:5000/student-dashboard.html`
- Access Admin Dashboard: `http://localhost:5000/admin-dashboard.html`
- Report a new issue: `http://localhost:5000/report.html`
- View issues map: `http://localhost:5000/map.html`

Enjoy using StreetFix Nairobi with XAMPP! 🚀
