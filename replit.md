# StreetFix Nairobi

## Overview
StreetFix Nairobi is a community infrastructure reporting platform that allows residents to report and track infrastructure issues in Nairobi. The application has been successfully migrated from MySQL to PostgreSQL and configured to run on Replit.

## Project Architecture

### Technology Stack
- **Backend**: Java 8 with Servlet API
- **Server**: Embedded Tomcat 9.0.80
- **Database**: PostgreSQL (Replit-hosted)
- **Frontend**: HTML, CSS, JavaScript
- **Build Tool**: Maven

### Project Structure
```
src/main/
├── java/com/streetfix/
│   ├── dao/              # Data Access Objects
│   ├── model/            # Data Models (User, Issue)
│   ├── servlet/          # API Servlets
│   └── EmbeddedServer.java  # Main application entry point
└── webapp/
    ├── admin-dashboard.html    # Admin portal
    ├── student-dashboard.html  # Student portal
    ├── dashboard.html          # General dashboard
    ├── index.html             # Login page
    ├── register.html          # Registration page
    ├── report.html            # Report submission
    ├── map.html               # Issues map
    ├── styles.css             # Application styles
    └── script.js              # Client-side logic
```

## Recent Changes (November 20, 2025)

### Database Migration
- Migrated from MySQL (XAMPP local DB) to PostgreSQL
- Updated `DatabaseConnection.java` to use environment variables
- Created PostgreSQL schema with `users` and `issues` tables

### Server Configuration
- Replaced WAR deployment with embedded Tomcat server
- Configured programmatic servlet registration (avoiding web.xml parsing issues)
- Added DefaultServlet for static file serving
- Bound server to `0.0.0.0:5000` for Replit compatibility

### UI Improvements
- Enhanced dashboard cards with larger, more prominent design
- Created separate dashboards:
  - **Student Dashboard**: For reporting and tracking personal issues
  - **Admin Dashboard**: For managing all community reports
- Improved action cards (3.5rem padding, 300px min-height)
- Enhanced hover effects and animations

### Dependencies Updated
- Added PostgreSQL JDBC driver (42.6.0)
- Added MySQL Connector (com.mysql:mysql-connector-j:8.0.33)
- Configured embedded Tomcat dependencies
- Dual database driver support

## Database Compatibility

**The application now supports BOTH databases:**
- **MySQL** (for XAMPP local development)
- **PostgreSQL** (for Replit hosted environment)

The application automatically detects which database to use:
- If `MYSQL_HOST` environment variable is set → Uses MySQL
- If `DATABASE_URL` or `PGHOST` is set → Uses PostgreSQL

### PostgreSQL URL Schemes Supported
The application now supports ALL common PostgreSQL DATABASE_URL formats:
- `postgres://user:pass@host:port/db` (Standard)
- `postgresql://user:pass@host:port/db` (Alternative)
- `jdbc:postgresql://host:port/db` (JDBC format)
- Automatic credential URL decoding
- Query parameter preservation
- Port defaulting to 5432

### XAMPP Setup
For local development with XAMPP, see `XAMPP_SETUP.md` for detailed instructions.
Use the `database_mysql.sql` file to create the MySQL database schema.

## Database Schema

### Users Table
```sql
- id (SERIAL PRIMARY KEY)
- full_name (VARCHAR 255)
- email (VARCHAR 255 UNIQUE)
- password (VARCHAR 255)
- created_at (TIMESTAMP)
```

### Issues Table
```sql
- id (SERIAL PRIMARY KEY)
- user_id (INTEGER, FOREIGN KEY)
- title (VARCHAR 255)
- description (TEXT)
- issue_type (VARCHAR 100)
- location (VARCHAR 255)
- latitude (DOUBLE PRECISION)
- longitude (DOUBLE PRECISION)
- status (VARCHAR 50, DEFAULT 'pending')
- photo_path (VARCHAR 500)
- created_at (TIMESTAMP)
```

## Environment Variables
The application uses the following environment variables (automatically set by Replit):
- `DATABASE_URL` - Full PostgreSQL connection string
- `PGHOST` - PostgreSQL host
- `PGPORT` - PostgreSQL port
- `PGDATABASE` - Database name
- `PGUSER` - Database username
- `PGPASSWORD` - Database password

## Running the Application

### Development
The application runs automatically via the configured workflow:
```bash
mvn compile exec:java
```

### Build
To build the WAR file:
```bash
mvn clean package
```

### Deployment
The application is configured for VM deployment on Replit, which maintains server state and runs continuously.

## User Guide

### For Students
1. Access the **Student Dashboard** at `/student-dashboard.html`
2. Use the large "Report New Issue" card to submit infrastructure problems
3. Track your reports using the "Track Your Reports" card
4. View statistics showing your total, pending, and resolved reports

### For Administrators
1. Access the **Admin Dashboard** at `/admin-dashboard.html`
2. Manage all community reports from the "Manage All Reports" card
3. Track all issues across Nairobi on the map
4. View comprehensive statistics: total reports, awaiting review, in progress, and resolved
5. Use admin actions to filter reports, export data, and view analytics

## API Endpoints

- `/register` - User registration (POST)
- `/login` - User authentication (POST)
- `/issues` - Issue management (GET, POST)
- `/dashboard` - Dashboard data (GET)

## Known Improvements
- Dashboard UI enhanced with larger, more prominent cards
- Separate portals for students and administrators
- Better visual hierarchy and user experience
- Improved hover effects and animations

## Technical Notes

### Embedded Tomcat Setup
The application uses programmatic servlet registration to avoid web.xml parsing issues:
- DefaultServlet for static files
- Custom servlets for API endpoints
- Welcome file set to `index.html`

### Database Connection
The application dynamically reads PostgreSQL credentials from environment variables and supports both DATABASE_URL format and individual credential format.

## Documentation Files

The project includes comprehensive documentation:
- **README.md** - Project overview and quick start
- **XAMPP_SETUP.md** - Complete XAMPP installation and configuration guide
- **INTELLIJ_SETUP.md** - Step-by-step IntelliJ IDEA setup instructions
- **FEATURES.md** - Complete feature list and technical documentation
- **database_mysql.sql** - MySQL database schema with sample data
- **replit.md** - This file (technical architecture and preferences)

## Future Enhancements
- Role-based authentication (admin vs. student)
- Real-time notifications for issue updates
- Advanced filtering and search capabilities
- Analytics dashboard with charts and insights
- Mobile-responsive design improvements
