# 🔒 StreetFix Nairobi - Security Documentation

**Security features, best practices, and guidelines for deployment**

---

## 🛡️ **Security Features Implemented**

### ✅ **1. Password Security (BCrypt Hashing)**

**Implementation:**
- All passwords are hashed using BCrypt with work factor 12
- Passwords are NEVER stored in plaintext
- One-way hashing ensures passwords cannot be reversed
- Strong salt generation for each password

**Technical Details:**
```java
// Password hashing (PasswordUtil.java)
BCrypt.hashpw(plainTextPassword, BCrypt.gensalt(12))

// Password verification
BCrypt.checkpw(plainTextPassword, hashedPassword)
```

**Work Factor 12:**
- Provides strong protection against brute-force attacks
- ~250ms to hash a password (intentionally slow)
- Computational cost increases exponentially with each increment

---

### ✅ **2. SQL Injection Prevention**

**Implementation:**
- All database queries use PreparedStatements
- User input is NEVER concatenated into SQL queries
- Parameterized queries prevent injection attacks

**Example:**
```java
// SECURE (used in codebase)
String sql = "SELECT * FROM users WHERE email = ?";
stmt.setString(1, userInput);

// INSECURE (NOT used)
String sql = "SELECT * FROM users WHERE email = '" + userInput + "'";
```

---

### ✅ **3. Session Management**

**Implementation:**
- Server-side sessions using HttpSession
- 30-minute timeout for inactivity
- Session data stored on server, not client
- Secure session cookies

**Configuration:**
```java
session.setMaxInactiveInterval(30 * 60); // 30 minutes
```

---

### ✅ **4. Input Validation**

**Implementation:**
- Server-side validation of all user inputs
- Email format validation
- Password minimum length (6 characters)
- Required field validation
- Duplicate email prevention

---

### ✅ **5. Sensitive Data Protection**

**Implementation:**
- Passwords excluded from API responses
- Hashed passwords NOT sent to client
- User data sanitized before JSON serialization

**Example:**
```java
// Login response NEVER includes password field
Map<String, Object> userData = new HashMap<>();
userData.put("id", user.getId());
userData.put("fullName", user.getFullName());
userData.put("email", user.getEmail());
// password field excluded!
```

---

## ⚠️ **Security Considerations for Deployment**

### **1. MySQL Database Security**

**Default XAMPP Configuration (INSECURE):**
```
Username: root
Password: (empty)
Port: 3306 (accessible from network)
```

**RECOMMENDED PRODUCTION SETUP:**

#### **Step 1: Set MySQL Root Password**
```sql
-- Open MySQL CLI or phpMyAdmin
ALTER USER 'root'@'localhost' IDENTIFIED BY 'YourStrongPassword123!';
FLUSH PRIVILEGES;
```

#### **Step 2: Create Dedicated Application User**
```sql
-- Create user for StreetFix application
CREATE USER 'streetfix_user'@'localhost' IDENTIFIED BY 'StrongAppPassword456!';

-- Grant only necessary permissions
GRANT SELECT, INSERT, UPDATE, DELETE ON streetfix_nairobi.* TO 'streetfix_user'@'localhost';
FLUSH PRIVILEGES;
```

#### **Step 3: Update Environment Variables**
```bash
# Use dedicated user instead of root
MYSQL_USER=streetfix_user
MYSQL_PASSWORD=StrongAppPassword456!
```

**Security Benefits:**
- ✅ Limits blast radius if credentials leak
- ✅ Prevents accidental database drops
- ✅ Follows principle of least privilege

---

### **2. Production Deployment Checklist**

Before deploying to production:

#### **Database Security**
- [ ] Change MySQL root password from empty default
- [ ] Create dedicated MySQL user for application
- [ ] Restrict database user permissions (no DROP, ALTER)
- [ ] Disable remote MySQL access if not needed
- [ ] Enable MySQL audit logging

#### **Application Security**
- [ ] Enable HTTPS/SSL (obtain SSL certificate)
- [ ] Set secure session cookie flags (httpOnly, secure)
- [ ] Implement rate limiting on login endpoint
- [ ] Add CSRF protection for state-changing operations
- [ ] Configure secure headers (X-Frame-Options, CSP, etc.)
- [ ] Disable stack traces in production error messages

#### **Environment Security**
- [ ] Never commit credentials to version control
- [ ] Use environment variables for all secrets
- [ ] Restrict file permissions on configuration files
- [ ] Keep dependencies updated (Maven, Java, libraries)
- [ ] Regular security audits of dependencies

---

### **3. Password Policy Recommendations**

**Current Policy:**
- Minimum length: 6 characters
- No complexity requirements

**RECOMMENDED FOR PRODUCTION:**
```java
// Enhanced password validation
public static boolean validatePassword(String password) {
    if (password.length() < 8) return false;
    if (!password.matches(".*[A-Z].*")) return false; // At least 1 uppercase
    if (!password.matches(".*[a-z].*")) return false; // At least 1 lowercase
    if (!password.matches(".*[0-9].*")) return false; // At least 1 digit
    if (!password.matches(".*[!@#$%^&*].*")) return false; // At least 1 special char
    return true;
}
```

**Recommended Policies:**
- Minimum 8 characters
- At least 1 uppercase letter
- At least 1 lowercase letter
- At least 1 number
- At least 1 special character
- Password expiration (90 days)
- Password history (prevent reuse of last 5 passwords)

---

### **4. Migration from Plaintext Passwords**

If you have existing users with plaintext passwords:

**⚠️ CRITICAL:** Do NOT deploy the new BCrypt version without migrating existing passwords.

**Option A: Force Password Reset (RECOMMENDED)**
```sql
-- Mark all existing users for password reset
ALTER TABLE users ADD COLUMN password_reset_required BOOLEAN DEFAULT FALSE;
UPDATE users SET password_reset_required = TRUE WHERE LENGTH(password) < 60;

-- Users will be prompted to reset password on next login
```

**Option B: One-Time Migration Script**
```java
// WARNING: Only run if you still have access to plaintext passwords
// This should be run ONCE during migration

public void migratePasswords() {
    String sql = "SELECT id, password FROM users WHERE LENGTH(password) < 60";
    
    try (Connection conn = DatabaseConnection.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        
        while (rs.next()) {
            int userId = rs.getInt("id");
            String plaintextPassword = rs.getString("password");
            
            String hashedPassword = PasswordUtil.hashPassword(plaintextPassword);
            
            String updateSql = "UPDATE users SET password = ? WHERE id = ?";
            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                updateStmt.setString(1, hashedPassword);
                updateStmt.setInt(2, userId);
                updateStmt.executeUpdate();
            }
        }
    }
}
```

---

## 🔐 **Additional Security Enhancements (Future)**

### **Planned Features:**

1. **Two-Factor Authentication (2FA)**
   - SMS or email verification codes
   - TOTP app support (Google Authenticator)

2. **Email Verification**
   - Verify email addresses on registration
   - Prevent fake account creation

3. **Account Lockout**
   - Lock account after 5 failed login attempts
   - Timed lockout (15 minutes)
   - CAPTCHA after 3 failed attempts

4. **Audit Logging**
   - Log all authentication events
   - Track IP addresses and timestamps
   - Monitor suspicious activity

5. **Password Reset Flow**
   - Secure token-based password reset
   - Email-based reset links
   - Time-limited reset tokens

6. **Content Security Policy (CSP)**
   - Prevent XSS attacks
   - Restrict resource loading
   - Block inline scripts

---

## 🧪 **Security Testing**

### **Manual Security Tests:**

1. **Password Hashing Test**
   ```bash
   # Create two accounts with same password
   # Check database: passwords should have different hashes
   SELECT email, password FROM users WHERE email IN ('user1@test.com', 'user2@test.com');
   ```

2. **SQL Injection Test**
   ```bash
   # Try to inject SQL in login form
   Email: admin@test.com' OR '1'='1
   Password: anything
   # Should fail authentication (not return all users)
   ```

3. **Session Timeout Test**
   ```bash
   # Login and wait 31 minutes
   # Try to access protected page
   # Should redirect to login
   ```

4. **Password Not in Response Test**
   ```bash
   # Login and check browser network tab
   # Response should NOT contain password or hashed password
   ```

### **Automated Security Testing:**

**Recommended Tools:**
- **OWASP ZAP** - Automated vulnerability scanner
- **SQLMap** - SQL injection testing
- **Burp Suite** - Web application security testing
- **JaCoCo** - Code coverage for security tests

---

## 📊 **Security Headers (Production)**

Add these headers to your production deployment:

```java
// In a filter or servlet
response.setHeader("X-Frame-Options", "DENY");
response.setHeader("X-Content-Type-Options", "nosniff");
response.setHeader("X-XSS-Protection", "1; mode=block");
response.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains");
response.setHeader("Content-Security-Policy", "default-src 'self'");
response.setHeader("Referrer-Policy", "no-referrer-when-downgrade");
```

---

## 🚨 **Security Incident Response**

### **If Credentials Are Compromised:**

1. **Immediate Actions:**
   - Change all database passwords immediately
   - Invalidate all active sessions
   - Force password reset for all users
   - Review audit logs for unauthorized access

2. **Investigation:**
   - Identify scope of breach
   - Determine what data was accessed
   - Preserve evidence for analysis

3. **Remediation:**
   - Patch vulnerabilities
   - Update dependencies
   - Strengthen access controls
   - Notify affected users (if required by law)

---

## 📞 **Reporting Security Issues**

If you discover a security vulnerability:

1. **DO NOT** publicly disclose the issue
2. Contact the development team privately
3. Provide detailed reproduction steps
4. Allow reasonable time for patching

**Responsible Disclosure Timeline:**
- Day 0: Report received
- Day 1-3: Vulnerability confirmed
- Day 4-7: Patch developed
- Day 8-14: Patch tested and deployed
- Day 15+: Public disclosure (if appropriate)

---

## ✅ **Current Security Status**

### **Implemented:**
✅ BCrypt password hashing (work factor 12)  
✅ SQL injection prevention (PreparedStatements)  
✅ Session management (30-min timeout)  
✅ Input validation  
✅ Sensitive data protection  
✅ Passwords excluded from API responses  

### **Recommended for Production:**
⚠️ HTTPS/SSL certificate  
⚠️ Secure session cookies (httpOnly, secure flags)  
⚠️ Rate limiting on authentication endpoints  
⚠️ CSRF protection  
⚠️ Security headers (CSP, X-Frame-Options, etc.)  
⚠️ Dedicated MySQL user (not root)  
⚠️ Strong MySQL password  
⚠️ Enhanced password policy (8+ chars, complexity)  

### **Future Enhancements:**
📅 Two-factor authentication  
📅 Email verification  
📅 Account lockout  
📅 Audit logging  
📅 Password reset flow  

---

## 📚 **Security Resources**

**Standards & Guidelines:**
- OWASP Top 10: https://owasp.org/www-project-top-ten/
- OWASP Cheat Sheets: https://cheatsheetseries.owasp.org/
- CWE Top 25: https://cwe.mitre.org/top25/

**Tools:**
- OWASP ZAP: https://www.zaproxy.org/
- Burp Suite: https://portswigger.net/burp
- SQLMap: https://sqlmap.org/

**Best Practices:**
- Java Security Guide: https://www.oracle.com/java/technologies/javase/seccodeguide.html
- Servlet Security: https://javaee.github.io/tutorial/security-intro.html

---

## 🎯 **Quick Security Checklist**

**Before Deployment:**
- [ ] All passwords hashed with BCrypt ✅
- [ ] No passwords in API responses ✅
- [ ] SQL injection prevented ✅
- [ ] MySQL root password changed ⚠️
- [ ] Dedicated MySQL user created ⚠️
- [ ] HTTPS enabled ⚠️
- [ ] Security headers configured ⚠️
- [ ] Session cookies secured ⚠️
- [ ] Rate limiting implemented ⚠️
- [ ] Dependencies updated ⚠️

---

**StreetFix Nairobi** - Security first, privacy always.

---

*Security Documentation Version: 1.0*  
*Last Updated: November 20, 2025*  
*Next Review: December 20, 2025*
