// StreetFix Nairobi - Frontend Application with Java Backend
// Updated to use API calls instead of localStorage

const API_BASE = ''; // Same server as frontend

class StreetFixAPI {
    // User Management
    static async registerUser(userData) {
        const formData = new FormData();
        formData.append('fullName', userData.full_name);
        formData.append('email', userData.email);
        formData.append('password', userData.password);

        try {
            const response = await fetch(`${API_BASE}/register`, {
                method: 'POST',
                body: formData
            });
            return await response.json();
        } catch (error) {
            throw new Error('Network error: ' + error.message);
        }
    }

    static async loginUser(email, password) {
        const formData = new FormData();
        formData.append('email', email);
        formData.append('password', password);

        try {
            const response = await fetch(`${API_BASE}/login`, {
                method: 'POST',
                body: formData
            });
            return await response.json();
        } catch (error) {
            throw new Error('Network error: ' + error.message);
        }
    }

    // Issue Management
    static async createIssue(issueData) {
        const formData = new FormData();
        formData.append('title', issueData.title);
        formData.append('description', issueData.description);
        formData.append('issueType', issueData.issue_type);
        formData.append('location', issueData.location);

        if (issueData.latitude) formData.append('latitude', issueData.latitude);
        if (issueData.longitude) formData.append('longitude', issueData.longitude);

        try {
            const response = await fetch(`${API_BASE}/issues`, {
                method: 'POST',
                body: formData
            });
            return await response.json();
        } catch (error) {
            throw new Error('Network error: ' + error.message);
        }
    }

    static async getDashboardData() {
        try {
            const response = await fetch(`${API_BASE}/dashboard`);
            return await response.json();
        } catch (error) {
            throw new Error('Network error: ' + error.message);
        }
    }

    static async getUserIssues() {
        try {
            const response = await fetch(`${API_BASE}/issues?type=user`);
            return await response.json();
        } catch (error) {
            throw new Error('Network error: ' + error.message);
        }
    }

    static async getAllIssues() {
        try {
            const response = await fetch(`${API_BASE}/issues`);
            return await response.json();
        } catch (error) {
            throw new Error('Network error: ' + error.message);
        }
    }
}

// Session management
class SessionManager {
    static getCurrentUser() {
        const userData = sessionStorage.getItem('current_user');
        return userData ? JSON.parse(userData) : null;
    }

    static setCurrentUser(user) {
        sessionStorage.setItem('current_user', JSON.stringify(user));
    }

    static clearCurrentUser() {
        sessionStorage.removeItem('current_user');
    }

    static isLoggedIn() {
        return this.getCurrentUser() !== null;
    }
}

// Utility Functions
function navigateTo(page) {
    window.location.href = page;
}

function logout() {
    // Call logout endpoint if you have one, or just clear session
    SessionManager.clearCurrentUser();
    navigateTo('index.html');
}

function showMessage(message, type = 'error') {
    const messageDiv = document.createElement('div');
    messageDiv.className = type === 'error' ? 'error-message' : 'success-message';
    messageDiv.textContent = message;

    document.body.insertBefore(messageDiv, document.body.firstChild);

    setTimeout(() => {
        messageDiv.remove();
    }, 5000);
}

// Form Handling
document.addEventListener('DOMContentLoaded', function() {
    // Login Form
    const loginForm = document.getElementById('loginForm');
    if (loginForm) {
        loginForm.addEventListener('submit', async function(e) {
            e.preventDefault();

            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;

            try {
                const result = await StreetFixAPI.loginUser(email, password);

                if (result.success) {
                    SessionManager.setCurrentUser(result.user);
                    showMessage('Login successful! Redirecting...', 'success');
                    setTimeout(() => navigateTo('dashboard.html'), 1000);
                } else {
                    showMessage(result.message);
                }
            } catch (error) {
                showMessage(error.message);
            }
        });
    }

    // Registration Form
    const registerForm = document.getElementById('registerForm');
    if (registerForm) {
        registerForm.addEventListener('submit', async function(e) {
            e.preventDefault();

            const userData = {
                full_name: document.getElementById('fullName').value,
                email: document.getElementById('email').value,
                password: document.getElementById('password').value
            };

            try {
                const result = await StreetFixAPI.registerUser(userData);

                if (result.success) {
                    showMessage('Registration successful! Please login.', 'success');
                    setTimeout(() => navigateTo('index.html'), 1500);
                } else {
                    showMessage(result.message);
                }
            } catch (error) {
                showMessage(error.message);
            }
        });
    }

    // Issue Report Form
    const issueForm = document.getElementById('issueForm');
    if (issueForm) {
        issueForm.addEventListener('submit', async function(e) {
            e.preventDefault();

            if (!SessionManager.isLoggedIn()) {
                showMessage('Please login to report issues');
                return;
            }

            const formData = new FormData(this);
            const issueData = {
                title: formData.get('title'),
                description: formData.get('description'),
                issue_type: formData.get('issueType'),
                location: formData.get('location'),
                latitude: window.currentLatitude || null,
                longitude: window.currentLongitude || null
            };

            try {
                const result = await StreetFixAPI.createIssue(issueData);

                if (result.success) {
                    showMessage('Issue reported successfully! Thank you for making Nairobi better.', 'success');
                    setTimeout(() => navigateTo('dashboard.html'), 1500);
                } else {
                    showMessage(result.message);
                }
            } catch (error) {
                showMessage('Error reporting issue: ' + error.message);
            }
        });
    }
});

// Dashboard Functions
async function loadDashboardData() {
    const currentUser = SessionManager.getCurrentUser();

    if (!currentUser) {
        navigateTo('index.html');
        return;
    }

    // Update welcome message
    const welcomeElement = document.getElementById('welcomeMessage');
    if (welcomeElement) {
        welcomeElement.textContent = `Welcome back, ${currentUser.fullName}!`;
    }

    try {
        // Load dashboard statistics
        const dashboardResult = await StreetFixAPI.getDashboardData();

        if (dashboardResult.success) {
            const stats = dashboardResult.stats;
            document.getElementById('totalReports').textContent = stats.total;
            document.getElementById('pendingReports').textContent = stats.pending;
            document.getElementById('inProgressReports').textContent = stats.inProgress;
            document.getElementById('resolvedReports').textContent = stats.resolved;

            // Update user impact
            const impactElement = document.getElementById('userImpact');
            if (impactElement) {
                impactElement.textContent = `${stats.userReports} issues reported`;
            }
        } else {
            showMessage('Failed to load dashboard data: ' + dashboardResult.message);
        }

        // Load user's recent reports
        await loadUserReports();

    } catch (error) {
        showMessage('Error loading dashboard: ' + error.message);
    }
}

async function loadUserReports() {
    const reportsList = document.getElementById('userReportsList');
    if (!reportsList) return;

    try {
        const result = await StreetFixAPI.getUserIssues();

        if (result.success) {
            const userReports = result.issues.slice(0, 5); // Get first 5

            if (userReports.length === 0) {
                reportsList.innerHTML = `
                    <div style="text-align: center; padding: 2rem; color: #666;">
                        <p style="font-size: 1.2rem; margin-bottom: 1rem;">You haven't reported any issues yet</p>
                        <a href="report.html" style="color: #4a7c59; font-weight: bold; text-decoration: none;">
                            🚀 Report Your First Issue!
                        </a>
                    </div>
                `;
            } else {
                reportsList.innerHTML = userReports.map(report => `
                    <div class="report-item">
                        <div>
                            <strong>${getIssueTypeIcon(report.issueType)} ${report.title}</strong>
                            <p>${report.description.substring(0, 80)}...</p>
                            <small>📍 ${report.location} • 📅 ${new Date(report.createdAt).toLocaleDateString()}</small>
                        </div>
                        <span class="report-status status-${report.status}">
                            ${report.status}
                        </span>
                    </div>
                `).join('');
            }
        } else {
            reportsList.innerHTML = '<p>Error loading reports</p>';
        }
    } catch (error) {
        reportsList.innerHTML = '<p>Error loading reports</p>';
        console.error('Error loading user reports:', error);
    }
}

// Map Functions
async function loadIssuesForMap() {
    try {
        const result = await StreetFixAPI.getAllIssues();
        return result.success ? result.issues : [];
    } catch (error) {
        console.error('Error loading issues for map:', error);
        return [];
    }
}

function getIssueTypeIcon(issueType) {
    const icons = {
        pothole: '🕳️',
        streetlight: '💡',
        trash: '🗑️',
        drainage: '🌊',
        other: '❓'
    };
    return icons[issueType] || '❓';
}

// Location Services
function getCurrentLocation() {
    if (!navigator.geolocation) {
        showMessage('Geolocation is not supported by this browser.');
        return;
    }

    const statusElement = document.getElementById('locationStatus');
    if (statusElement) {
        statusElement.textContent = '📍 Getting your location...';
        statusElement.style.background = '#fff3cd';
        statusElement.style.color = '#856404';
    }

    navigator.geolocation.getCurrentPosition(
        (position) => {
            window.currentLatitude = position.coords.latitude;
            window.currentLongitude = position.coords.longitude;

            if (statusElement) {
                statusElement.textContent = '✅ Location captured successfully!';
                statusElement.style.background = '#d4edda';
                statusElement.style.color = '#155724';
            }

            // Update location input with coordinates
            const locationInput = document.getElementById('location');
            if (locationInput) {
                locationInput.value = `Near coordinates: ${position.coords.latitude.toFixed(4)}, ${position.coords.longitude.toFixed(4)}`;
            }
        },
        (error) => {
            if (statusElement) {
                statusElement.textContent = '❌ Unable to get location. Please enter manually.';
                statusElement.style.background = '#f8d7da';
                statusElement.style.color = '#721c24';
            }
            showMessage('Location error: ' + error.message);
        },
        {
            enableHighAccuracy: true,
            timeout: 10000,
            maximumAge: 60000
        }
    );
}

// Authentication Check
function checkAuthentication() {
    const publicPages = ['index.html', 'register.html'];
    const currentPage = window.location.pathname.split('/').pop();

    if (!publicPages.includes(currentPage) && !SessionManager.isLoggedIn()) {
        navigateTo('index.html');
    }
}

// Initialize authentication check
document.addEventListener('DOMContentLoaded', checkAuthentication);

// Export functions for use in map.html
window.getIssuesFromStorage = loadIssuesForMap;
window.logout = logout;
window.navigateTo = navigateTo;
window.getCurrentLocation = getCurrentLocation;