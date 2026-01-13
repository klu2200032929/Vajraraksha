================================================================================
                                VAJRARAKSHA
                   Cybersecurity Awareness & Training Platform
================================================================================

PROJECT OVERVIEW
--------------------------------------------------------------------------------
VajraRaksha is a Spring Boot application designed to train users in 
cybersecurity concepts through interactive labs and courses.

KEY FEATURES
--------------------------------------------------------------------------------
1. Roles: Admin, Instructor, Student
2. Interactive Terminal: Simulated Linux environment for labs.
3. Course Viewer: Video and quiz integration.
4. Analytics: Admin dashboard with charts.
5. Gamification: Points and badging system.

HOW TO RUN
--------------------------------------------------------------------------------
Prerequisites:
- Java JDK 17+
- Maven (or use IDE with Maven support)
- MongoDB (Configured in application.properties)

Method 1: Using IDE (Recommended)
1. Open this folder in IntelliJ IDEA, Eclipse, or VS Code.
2. Wait for Maven dependencies to import.
3. Run 'VajraRakshaApplication.java' as a Java Application.
4. Open browser to http://localhost:8081

Method 2: Using Command Line (If Maven is installed)
1. mvn spring-boot:run

DEFAULT LOGIN CREDENTIALS
--------------------------------------------------------------------------------
The application automatically creates these users on startup:

Role        | Username    | Password
------------|-------------|-------------
Admin       | admin       | admin123
Instructor  | instructor  | instructor123
Student     | student     | student123

TROUBLESHOOTING
--------------------------------------------------------------------------------
- If the port 8081 is in use, change 'server.port' in 
  src/main/resources/application.properties.
- Database connection errors? Check your MongoDB Atlas URI in 
  application.properties.

================================================================================
Built by VajraRaksha Team
================================================================================
