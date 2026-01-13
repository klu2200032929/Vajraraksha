package com.vajraraksha.config;

import com.vajraraksha.model.Role;
import com.vajraraksha.model.User;
import com.vajraraksha.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private com.vajraraksha.repository.CourseRepository courseRepository;
    @Autowired
    private com.vajraraksha.repository.LabRepository labRepository;
    @Autowired
    private com.vajraraksha.repository.QuizRepository quizRepository;

    @Override
    public void run(String... args) throws Exception {
        // --- 1. USER SEEDING ---
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);
            admin.setEmail("admin@vajraraksha.com");
            userRepository.save(admin);
        }

        if (!userRepository.existsByUsername("instructor")) {
            User instructor = new User();
            instructor.setUsername("instructor");
            instructor.setPassword(passwordEncoder.encode("instructor123"));
            instructor.setRole(Role.INSTRUCTOR);
            instructor.setEmail("instructor@vajraraksha.com");
            userRepository.save(instructor);
        }

        if (!userRepository.existsByUsername("student")) {
            User student = new User();
            student.setUsername("student");
            student.setPassword(passwordEncoder.encode("student123"));
            student.setRole(Role.STUDENT);
            student.setEmail("student@vajraraksha.com");
            student.setPoints(150); // Give some starting points for leaderboard
            userRepository.save(student);
        }

        // --- 2. COURSE SEEDING ---
        if (courseRepository.count() == 0) {
            // Course 1: Network Security
            com.vajraraksha.model.Course netCourse = new com.vajraraksha.model.Course();
            netCourse.setTitle("Network Security Fundamentals");
            netCourse.setDescription("Master the art of packet analysis, port scanning, and firewall configuration.");
            netCourse.setInstructorId("instructor");

            com.vajraraksha.model.Lesson l1 = new com.vajraraksha.model.Lesson("Understanding TCP/IP",
                    "Deep dive into the protocols that power the internet.",
                    "https://www.youtube.com/embed/3u7_8qM0tgo");
            com.vajraraksha.model.Lesson l2 = new com.vajraraksha.model.Lesson("Port Scanning with Nmap",
                    "How to identify open ports and potential vulnerabilities.",
                    "https://www.youtube.com/embed/HkZ8OW1X6Qk");

            netCourse.getLessons().add(l1);
            netCourse.getLessons().add(l2);
            courseRepository.save(netCourse);

            // Quiz for Network Course
            com.vajraraksha.model.Quiz netQuiz = new com.vajraraksha.model.Quiz();
            netQuiz.setTitle("Network Defense Quiz");
            netQuiz.setLessonId(netCourse.getId());
            netQuiz.setTotalPoints(100);
            netQuiz.setQuestions(new java.util.ArrayList<>());
            netQuiz.getQuestions().add(new com.vajraraksha.model.Question("Which protocol is connection-oriented?",
                    java.util.Arrays.asList("UDP", "TCP", "ICMP", "IP"), 1, "TCP establishes a reliable connection."));
            netQuiz.getQuestions().add(new com.vajraraksha.model.Question("Default port for SSH?",
                    java.util.Arrays.asList("21", "23", "22", "80"), 2, "SSH runs on port 22."));
            quizRepository.save(netQuiz);

            // Course 2: Web Application Security
            com.vajraraksha.model.Course webCourse = new com.vajraraksha.model.Course();
            webCourse.setTitle("Web App Hacking");
            webCourse.setDescription("Learn to exploit and patch OWASP Top 10 vulnerabilities like SQLi and XSS.");
            webCourse.setInstructorId("instructor");

            webCourse.getLessons().add(new com.vajraraksha.model.Lesson("SQL Injection 101",
                    "How attackers steal data via input fields.", "https://www.youtube.com/embed/_jKylhJtGsM"));
            webCourse.getLessons().add(new com.vajraraksha.model.Lesson("Cross-Site Scripting (XSS)",
                    "Injecting malicious scripts into web pages.", "https://www.youtube.com/embed/EoaDgUgS6QA"));
            courseRepository.save(webCourse);

            // Course 3: Cryptography
            com.vajraraksha.model.Course cryptoCourse = new com.vajraraksha.model.Course();
            cryptoCourse.setTitle("Applied Cryptography");
            cryptoCourse.setDescription("Secure communication using symmetric and asymmetric encryption.");
            cryptoCourse.setInstructorId("instructor");

            cryptoCourse.getLessons().add(new com.vajraraksha.model.Lesson("Symmetric vs Asymmetric",
                    "Keys, ciphers, and algorithms.", "https://www.youtube.com/embed/AQDCeUEOgG0"));
            courseRepository.save(cryptoCourse);
        }

        // --- 3. LAB SEEDING ---
        if (labRepository.count() == 0) {
            // Lab 1: Nmap
            com.vajraraksha.model.Lab lab1 = new com.vajraraksha.model.Lab();
            lab1.setTitle("Operation: Port Hunter");
            lab1.setDescription("Target IP: 10.10.5.55. Your mission is to identify all open services.");
            lab1.setDifficulty("Intermediate");
            lab1.setInstructorId("instructor");
            lab1.setPoints(200);
            lab1.setSteps(new java.util.ArrayList<>());

            lab1.getSteps().add(new com.vajraraksha.model.LabStep(1, "Verify Tool Installation", "nmap --version",
                    "Nmap version 7.92 ( https://nmap.org )", "Always check your tools before engagement."));
            lab1.getSteps().add(new com.vajraraksha.model.LabStep(2, "Ping Sweep", "nmap -sn 10.10.5.0/24",
                    "Nmap scan report for 10.10.5.55", "Locate live hosts in the subnet."));
            lab1.getSteps().add(new com.vajraraksha.model.LabStep(3, "Service Detection", "nmap -sV 10.10.5.55",
                    "80/tcp open http Apache httpd 2.4.49", "Identify service versions for vulnerabilities."));

            labRepository.save(lab1);

            // Lab 2: SQL Injection
            com.vajraraksha.model.Lab lab2 = new com.vajraraksha.model.Lab();
            lab2.setTitle("Project: Glass House");
            lab2.setDescription("Infiltrate the login portal using SQL Injection techniques.");
            lab2.setDifficulty("Advanced");
            lab2.setInstructorId("instructor");
            lab2.setPoints(500);
            lab2.setSteps(new java.util.ArrayList<>());

            lab2.getSteps()
                    .add(new com.vajraraksha.model.LabStep(1, "Test Vulnerability",
                            "sqlmap -u 'http://target.com/login'",
                            "[INFO] testing connection to the target URL", "Automated testing for SQLi flaws."));

            labRepository.save(lab2);
        }

        System.out.println("--- DataInitializer: Full Premium Content Seeded ---");
    }
}
