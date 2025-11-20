# 🚧 StreetFix Nairobi

**A modern community infrastructure reporting platform for Nairobi residents**

![Version](https://img.shields.io/badge/version-1.0.0-blue)
![Java](https://img.shields.io/badge/Java-8%2B-orange)
![License](https://img.shields.io/badge/license-MIT-green)

---

## 📖 Overview

StreetFix Nairobi is a full-featured web application that allows residents to report and track infrastructure issues in Nairobi. With separate portals for students and administrators, advanced UI components, and dual database support, it's ready for both local development and cloud deployment.

### ✨ Key Features

- 🎨 **Modern UI** - Beautiful glassmorphism effects, gradients, and smooth animations
- 👥 **Dual Portals** - Separate dashboards for students and administrators
- 📊 **Real-time Tracking** - Live issue status updates and notifications
- 🗺️ **Interactive Map** - Visualize all reported issues on Nairobi map
- 🔐 **Secure Authentication** - User registration and login with encrypted passwords
- 🗄️ **Dual Database Support** - MySQL (XAMPP) for local, PostgreSQL (Replit) for cloud
- 📱 **Responsive Design** - Works on desktop, tablet, and mobile devices

---

## 🚀 Quick Start

### Prerequisites

- **IntelliJ IDEA** (Community or Ultimate)
- **JDK 8 or higher**
- **XAMPP** (Apache + MySQL)
- **Maven** (bundled with IntelliJ)

### Installation (5 Steps)

**1. Clone/Download Project**
```bash
# If using git
git clone <repository-url>
cd streetfix-nairobi

# Or download and extract ZIP
```

**2. Setup XAMPP Database**
```bash
# Start XAMPP (Apache + MySQL)
# Open phpMyAdmin: http://localhost/phpmyadmin
# Create database: streetfix_nairobi
# Import: database_mysql.sql
```

**3. Open in IntelliJ**
```bash
# File → Open → Select project folder
# Wait for Maven dependencies to download
```

**4. Configure Environment**
```bash
# Run → Edit Configurations → Add Application
# Main class: com.streetfix.EmbeddedServer
# Environment Variables:
MYSQL_HOST=localhost
MYSQL_PORT=3306
MYSQL_DATABASE=streetfix_nairobi
MYSQL_USER=root
MYSQL_PASSWORD=
```

**5. Run Application**
```bash
# Click Run button or press Shift+F10
# Open browser: http://localhost:5000
```

📚 **For detailed setup instructions, see [INTELLIJ_SETUP.md](INTELLIJ_SETUP.md)**

---

## 🎯 Features

### For Students
- ✅ Report infrastructure issues with photos and location
- ✅ Track personal reports and status updates
- ✅ View statistics (total, pending, resolved)
- ✅ Receive real-time notifications
- ✅ Search and filter own reports

### For Administrators
- ✅ Manage all community reports
- ✅ View community-wide statistics
- ✅ Track performance metrics (response time, resolution rate)
- ✅ Export data and analytics
- ✅ Send notifications to users
- ✅ Advanced search and filtering

### Technical Features
- ✅ Embedded Tomcat server
- ✅ RESTful API architecture
- ✅ Session management
- ✅ Password encryption
- ✅ Dual database support (MySQL/PostgreSQL)
- ✅ Maven build system
- ✅ WAR packaging for deployment

📚 **For complete feature list, see [FEATURES.md](FEATURES.md)**

---

## 🗂️ Project Structure

```
streetfix-nairobi/
├── src/main/
│   ├── java/com/streetfix/       # Backend code
│   │   ├── dao/                  # Database access
│   │   ├── model/                # Data models
│   │   ├── servlet/              # API endpoints
│   │   └── EmbeddedServer.java   # Main application
│   └── webapp/                   # Frontend files
│       ├── index.html            # Login page
│       ├── register.html         # Signup page
│       ├── student-dashboard.html # Student portal
│       ├── admin-dashboard.html   # Admin portal
│       ├── report.html           # Report form
│       ├── map.html              # Issues map
│       ├── styles.css            # Styles
│       └── script.js             # JavaScript
├── database_mysql.sql            # MySQL schema
├── pom.xml                       # Maven config
├── README.md                     # This file
├── INTELLIJ_SETUP.md            # Setup guide
├── XAMPP_SETUP.md               # XAMPP guide
├── FEATURES.md                  # Feature docs
└── DEPLOYMENT_GUIDE.md          # Deployment guide
```

---

## 🧪 Test Accounts

The application comes with pre-configured test accounts:

**Admin Account:**
- Email: `admin@streetfix.co.ke`
- Password: `admin123`

**Student Account:**
- Email: `john@example.com`
- Password: `password123`

---

## 🗄️ Database

### MySQL (Local Development)
- **Default XAMPP Configuration**
- Host: `localhost`
- Port: `3306`
- Database: `streetfix_nairobi`
- User: `root`
- Password: *(empty)*

### PostgreSQL (Cloud Deployment)
- **Automatic on Replit**
- Supports all URL formats
- Environment-based configuration
- No manual setup required

---

## 🛠️ Development

### Build Commands

```bash
# Clean and compile
mvn clean compile

# Create WAR file
mvn clean package

# Run application
mvn clean compile exec:java

# Run tests
mvn test
```

### IntelliJ Shortcuts

- **Run**: `Shift + F10`
- **Debug**: `Shift + F9`
- **Find Class**: `Ctrl + N`
- **Find File**: `Ctrl + Shift + N`
- **Git Commit**: `Ctrl + K`

---

## 📡 API Endpoints

### Authentication
- `POST /register` - User registration
- `POST /login` - User login
- `POST /logout` - User logout

### Issues
- `GET /issues` - Get all issues
- `POST /issues` - Create new issue
- `GET /issues/:id` - Get specific issue
- `PUT /issues/:id` - Update issue status

### Dashboard
- `GET /dashboard` - Get dashboard statistics
- `GET /dashboard/user/:id` - Get user-specific data

---

## 🎨 UI Design

### Color Palette
- **Primary Green**: `#4a7c59`, `#2c5530`
- **Accent Purple**: `#667eea`, `#764ba2`
- **Alert Red**: `#ff6b6b`
- **Status Colors**: Yellow (Pending), Blue (In Progress), Green (Resolved)

### Animations
- Bounce animation for icons
- Pulse effect for badges
- Count-up for statistics
- Slide-in for cards
- Hover elevation effects

---

## 🔒 Security

- ✅ Password encryption using secure hashing
- ✅ SQL injection prevention with prepared statements
- ✅ XSS protection
- ✅ Session management
- ✅ Input validation
- ✅ Environment variable secrets

---

## 📱 Screenshots

### Login Page
Beautiful purple gradient with glassmorphism effects

### Registration
Green gradient with real-time password strength meter

### Student Dashboard
Personal statistics, notifications, and quick actions

### Admin Dashboard
Community metrics, performance stats, and management tools

---

## 🚢 Deployment

### Local Development (XAMPP)
```bash
mvn clean compile exec:java
# Access: http://localhost:5000
```

### WAR Deployment (Tomcat)
```bash
mvn clean package
# Deploy: target/streetfix.war to Tomcat webapps/
```

### Cloud Deployment (Replit)
```bash
# Automatic PostgreSQL detection
# No configuration needed
```

📚 **For deployment details, see [DEPLOYMENT_GUIDE.md](DEPLOYMENT_GUIDE.md)**

---

## 📚 Documentation

| Document | Description |
|----------|-------------|
| [README.md](README.md) | This file - Project overview |
| [INTELLIJ_SETUP.md](INTELLIJ_SETUP.md) | Complete IntelliJ IDEA setup guide |
| [XAMPP_SETUP.md](XAMPP_SETUP.md) | XAMPP installation and configuration |
| [FEATURES.md](FEATURES.md) | Comprehensive feature documentation |
| [DEPLOYMENT_GUIDE.md](DEPLOYMENT_GUIDE.md) | Deployment instructions |
| [database_mysql.sql](database_mysql.sql) | MySQL database schema with sample data |

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 🐛 Troubleshooting

### Common Issues

**Cannot connect to database**
- Verify XAMPP MySQL is running
- Check database exists
- Verify environment variables

**Port 5000 already in use**
- Stop other applications on port 5000
- Or change port in `EmbeddedServer.java`

**Build failures**
- Check JDK version (8+)
- Reload Maven project
- Invalidate IntelliJ caches

📚 **See [DEPLOYMENT_GUIDE.md](DEPLOYMENT_GUIDE.md) for detailed troubleshooting**

---

## 📞 Support

- 📧 Email: support@streetfix.co.ke
- 📖 Documentation: See docs folder
- 🐛 Issues: GitHub Issues
- 💬 Community: Discussion board

---

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

## 🙏 Acknowledgments

- Built with Java, Maven, and Tomcat
- UI inspired by modern glassmorphism design trends
- Database support for MySQL and PostgreSQL
- Designed for Nairobi infrastructure reporting

---

## 🎯 Roadmap

### Version 1.0 (Current)
- ✅ User authentication
- ✅ Issue reporting and tracking
- ✅ Student and admin dashboards
- ✅ Interactive map view
- ✅ Dual database support

### Version 2.0 (Planned)
- 📧 Email notifications
- 📱 Mobile app
- 🌐 Multi-language support
- 📊 Advanced analytics
- 🔔 Push notifications
- 🌙 Dark mode

---

**Made with ❤️ for Nairobi**

*Making infrastructure reporting modern, efficient, and beautiful!*

---

## 🚀 Getting Started

Ready to deploy? Follow these steps:

1. ✅ Read [INTELLIJ_SETUP.md](INTELLIJ_SETUP.md)
2. ✅ Setup XAMPP with [XAMPP_SETUP.md](XAMPP_SETUP.md)
3. ✅ Import project in IntelliJ
4. ✅ Configure environment variables
5. ✅ Run and test with sample accounts
6. ✅ Start reporting issues!

**Happy coding!** 🎉
