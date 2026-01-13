package com.vajraraksha.service;

import com.vajraraksha.model.Question;
import com.vajraraksha.model.Quiz;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class QuizGeneratorService {

        private final Random random = new Random();

        public Quiz generateQuiz(String lessonTitle, String topic) {
                Quiz quiz = new Quiz();
                quiz.setTitle("Quiz: " + lessonTitle);
                quiz.setTotalPoints(20);
                quiz.setQuestions(generateQuestions(topic, 3)); // Generate 3 questions per quiz
                return quiz;
        }

        public List<Question> getUniqueQuestions(String topic, int count) {
                List<Question> allQuestions = getQuestionBank(topic.toLowerCase());
                Collections.shuffle(allQuestions);

                // If we need more questions than available, we might have to repeat or handle
                // gracefully.
                // For now, we return as many unique ones as we have, then loop if absolutely
                // necessary
                // (though robust bank should prevent this).
                if (count <= allQuestions.size()) {
                        return allQuestions.subList(0, count);
                } else {
                        // Not enough unique questions, fill with duplicates (or maybe generic ones)
                        List<Question> result = new ArrayList<>(allQuestions);
                        int needed = count - allQuestions.size();
                        for (int i = 0; i < needed; i++) {
                                result.add(allQuestions.get(i % allQuestions.size()));
                        }
                        return result;
                }
        }

        private List<Question> getQuestionBank(String topic) {
                List<Question> bank = new ArrayList<>();

                if (topic.contains("sql")) {
                        bank.add(new Question("What is SQL Injection?",
                                        List.of("A code injection technique", "A database design pattern",
                                                        "A network protocol", "A Java library"),
                                        "A code injection technique"));
                        bank.add(new Question("Which character is commonly used to test for SQLi?",
                                        List.of("Single Quote (')", "Backslash (\\)", "Pipe (|)", "Currency ($)"),
                                        "Single Quote (')"));
                        bank.add(new Question("What does '1'='1' represent in SQLi?",
                                        List.of("A Tautology (Always True)", "A Contradiction", "A Syntax Error",
                                                        "A Variable assignment"),
                                        "A Tautology (Always True)"));
                        bank.add(new Question("Which SQL statement is most dangerous for data loss?",
                                        List.of("DROP TABLE", "SELECT *", "UPDATE WHERE", "INSERT INTO"),
                                        "DROP TABLE"));
                        bank.add(new Question("How can you prevent SQL Injection?",
                                        List.of("Prepared Statements", "Input Validation only", "Using GET requests",
                                                        "Hiding error messages"),
                                        "Prepared Statements"));
                        // New Questions
                        bank.add(new Question("What is Blind SQL Injection?",
                                        List.of("Inferring data from server responses",
                                                        "Injecting code into image files",
                                                        "Stealing passwords visually", "None of the above"),
                                        "Inferring data from server responses"));
                        bank.add(new Question("Which of these is a Time-Based Blind SQLi technique?",
                                        List.of("SLEEP(5)", "WAIT FOR DELAY", "Both A and B", "None"), "Both A and B"));
                        bank.add(new Question("What is UNION-based SQL injection?",
                                        List.of("Combining results of two queries", "Joining two tables normally",
                                                        "Union of labor rights", "None"),
                                        "Combining results of two queries"));
                        bank.add(new Question("Which tool is commonly used to automate SQL injection detection?",
                                        List.of("SQLMap", "Nmap", "Wireshark", "Metasploit"), "SQLMap"));
                        bank.add(new Question("In a Second-Order SQL Injection, where is the payload stored?",
                                        List.of("Database", "URL", "Cookie", "Browser Cache"), "Database"));
                        bank.add(new Question("What does the comment sequence '--' do in SQL?",
                                        List.of("Ignores the rest of the query", "Executes the query twice",
                                                        "Deletes the table", "Nothing"),
                                        "Ignores the rest of the query"));
                } else if (topic.contains("xss")) {
                        bank.add(new Question("What does XSS stand for?",
                                        List.of("Cross-Site Scripting", "Extra Secure Sockets", "XML Site Styling",
                                                        "Xenon Server Script"),
                                        "Cross-Site Scripting"));
                        bank.add(new Question("Which type of XSS is stored in the database?",
                                        List.of("Stored (Persistent)", "Reflected", "DOM-based", "Client-side"),
                                        "Stored (Persistent)"));
                        bank.add(new Question("Which HTML tag is most commonly associated with XSS payloads?",
                                        List.of("<script>", "<div>", "<p>", "<span>"), "<script>"));
                        bank.add(new Question("What is a common impact of XSS?",
                                        List.of("Session Hijacking", "Server Crash", "Database Deletion",
                                                        "Network Sniffing"),
                                        "Session Hijacking"));
                        bank.add(new Question("Which header helps prevent XSS?",
                                        List.of("Content-Security-Policy", "X-Frame-Options",
                                                        "Strict-Transport-Security", "Cache-Control"),
                                        "Content-Security-Policy"));
                        // New Questions
                        bank.add(new Question("What is DOM-based XSS?",
                                        List.of("Vulnerability in client-side scripts", "A server-side error",
                                                        "Database injection", "None"),
                                        "Vulnerability in client-side scripts"));
                        bank.add(new Question("Which character encoding can sometimes bypass XSS filters?",
                                        List.of("URL Encoding", "Base64", "Hex", "All of the above"),
                                        "All of the above"));
                        bank.add(new Question("What is a 'Reflected' XSS attack?",
                                        List.of("Malicious script reflected off a web server", "Script saved in DB",
                                                        "Script in local file", "None"),
                                        "Malicious script reflected off a web server"));
                        bank.add(new Question("Is input validation sufficient to prevent XSS?",
                                        List.of("No, output encoding is also needed", "Yes, absolutely",
                                                        "Only for GET requests", "None"),
                                        "No, output encoding is also needed"));
                        bank.add(new Question("Which javascript function is dangerous and can lead to XSS if misused?",
                                        List.of("eval()", "alert()", "console.log()", "Math.random()"), "eval()"));
                } else if (topic.contains("phishing")) {
                        bank.add(new Question("What is Phishing?",
                                        List.of("Deceptive attempt to get sensitive info", "Fishing for data in a lake",
                                                        "Network scanning", "Password cracking"),
                                        "Deceptive attempt to get sensitive info"));
                        bank.add(new Question("What is Spear Phishing?",
                                        List.of("Targeted attack on specific person/group", "Random email spam",
                                                        "Voice phishing", "SMS phishing"),
                                        "Targeted attack on specific person/group"));
                        bank.add(new Question("What is a common sign of a phishing email?",
                                        List.of("Sense of urgency", "Personalized greeting", "Valid sender address",
                                                        "Plain text format"),
                                        "Sense of urgency"));
                        // New Questions
                        bank.add(new Question("What is 'Whaling' in the context of phishing?",
                                        List.of("Targeting high-profile executives", "Phishing for large files",
                                                        "Targeting marine biology sites", "None"),
                                        "Targeting high-profile executives"));
                        bank.add(new Question("What is 'Vishing'?",
                                        List.of("Voice Phishing", "Video Phishing", "Virtual Phishing", "None"),
                                        "Voice Phishing"));
                        bank.add(new Question("What is a homograph attack?",
                                        List.of("Using look-alike characters in URLs", "Attacking smart homes",
                                                        "attacking graphics", "None"),
                                        "Using look-alike characters in URLs"));
                        bank.add(new Question("What should you do if you suspect an email is phishing?",
                                        List.of("Verify with sender via another channel", "Click links to check",
                                                        "Reply and ask", "Forward to friends"),
                                        "Verify with sender via another channel"));
                        bank.add(new Question("What is 'Smishing'?",
                                        List.of("SMS Phishing", "Smart Phishing", "Small Phishing", "None"),
                                        "SMS Phishing"));
                } else {
                        // General Security Questions
                        bank.add(new Question("confidentiality, integrity, and availability are known as?",
                                        List.of("The CIA Triad", "The Security Triangle", "The Golden Rules",
                                                        "The Three Amigos"),
                                        "The CIA Triad"));
                        bank.add(new Question("What is the principle of Least Privilege?",
                                        List.of("Giving minimum necessary access", "Giving full admin access",
                                                        "No access at all", "Guest access only"),
                                        "Giving minimum necessary access"));
                        bank.add(new Question("What is a Zero Day vulnerability?",
                                        List.of("A flaw unknown to the vendor", "A flaw fixed 0 days ago",
                                                        "A non-critical bug", "A server crash"),
                                        "A flaw unknown to the vendor"));
                        // New Questions
                        bank.add(new Question("What is Multifactor Authentication (MFA)?",
                                        List.of("Using two or more verification methods", "Using a long password",
                                                        "Changing password often", "None"),
                                        "Using two or more verification methods"));
                        bank.add(new Question("What is 'Social Engineering'?",
                                        List.of("Manipulating people into giving up confidential info",
                                                        "Building social networks", "Hacking servers directly", "None"),
                                        "Manipulating people into giving up confidential info"));
                        bank.add(new Question("What is Ransomware?",
                                        List.of("Malware that encrypts files and demands payment", "Free software",
                                                        "Antivirus software", "None"),
                                        "Malware that encrypts files and demands payment"));
                        bank.add(new Question("What is a Man-in-the-Middle attack?",
                                        List.of("Attacker intercepts communication between two parties",
                                                        "Attacker sits in the server room",
                                                        "Attacker hacks the middle server", "None"),
                                        "Attacker intercepts communication between two parties"));
                        bank.add(new Question("What is proper password hygiene?",
                                        List.of("Long, unique, complex passwords", "Using 'password123'",
                                                        "Sharing passwords", "Writing them on post-its"),
                                        "Long, unique, complex passwords"));
                }

                return bank;
        }
}
