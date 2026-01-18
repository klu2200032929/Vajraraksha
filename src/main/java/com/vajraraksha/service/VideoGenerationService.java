package com.vajraraksha.service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class VideoGenerationService {

    private final Map<String, String[]> videoDatabase = new HashMap<>();
    private final Random random = new Random();

    public VideoGenerationService() {
        initializeDatabase();
    }

    /**
     * Simulates an AI Video Generator.
     * Analyzes the topic semantic context and generates (selects) a relevant video.
     * Returns a URL (YouTube Embed or direct MP4).
     */
    public String getVideoForTopic(String topic, int lessonIndex) {
        String lowerTopic = topic.toLowerCase();
        String[] options = null;

        // 1. Exact/High-Confidence Keyword Matching
        if (lowerTopic.contains("sql") && lowerTopic.contains("injection")) {
            options = videoDatabase.get("sqli");
        } else if (lowerTopic.contains("xss") || lowerTopic.contains("cross-site")) {
            options = videoDatabase.get("xss");
        } else if (lowerTopic.contains("phishing")) {
            options = videoDatabase.get("phishing");
        } else if (lowerTopic.contains("malware") || lowerTopic.contains("virus")
                || lowerTopic.contains("ransomware")) {
            options = videoDatabase.get("malware");
        } else if (lowerTopic.contains("cryptography") || lowerTopic.contains("encryption")) {
            options = videoDatabase.get("crypto");
        } else if (lowerTopic.contains("firewall") || lowerTopic.contains("network")) {
            options = videoDatabase.get("network");
        } else if (lowerTopic.contains("cloud")) {
            options = videoDatabase.get("cloud");
        } else if (lowerTopic.contains("password") || lowerTopic.contains("authentication")) {
            options = videoDatabase.get("auth");
        }

        // 2. Deterministic Selection
        if (options != null && options.length > 0) {
            // If we have videos, pick the one at index.
            // If index is out of bounds, return null (Fallback to text only)
            if (lessonIndex < options.length) {
                return options[lessonIndex];
            } else {
                return null;
            }
        }

        // 3. Fallback for "general" or unknown topics
        // For general, we might still want rotation if we have multiple
        String[] general = videoDatabase.get("general");
        if (general != null && lessonIndex < general.length) {
            return general[lessonIndex];
        }

        return null;
    }

    private String selectRandom(String[] options) {
        if (options == null || options.length == 0)
            return "https://www.youtube.com/embed/inWWhr5tnEA";
        return options[random.nextInt(options.length)];
    }

    private void initializeDatabase() {
        videoDatabase.put("sqli", new String[] {
                "https://www.youtube.com/embed/8XVHftQskxk", // SQL Injection Explained
                "https://www.youtube.com/embed/wcaiKgQU6VE", // SQL Injection in 5 Minutes
        });

        videoDatabase.put("xss", new String[] {
                "https://www.youtube.com/embed/EoaDgUgS6QA", // XSS in 100 seconds
                "https://www.youtube.com/embed/z4LhLJnmoZ0", // XSS Explained
        });

        videoDatabase.put("phishing", new String[] {
                "https://www.youtube.com/embed/XBkzBrXlle0", // Phishing Explained
                "https://www.youtube.com/embed/Y7zNlEMDmI4", // Phishing Attacks
        });

        videoDatabase.put("malware", new String[] {
                "https://www.youtube.com/embed/VJFaO2-zsCU", // Malware Explained
                "https://www.youtube.com/embed/mqzP7gJDM2s", // Malware Analysis
        });

        videoDatabase.put("crypto", new String[] {
                "https://www.youtube.com/embed/jhXCTbFnK8o", // Cryptography Basics
                "https://www.youtube.com/embed/GQvu49c0ZZc", // Encryption Explained
        });

        videoDatabase.put("network", new String[] {
                "https://www.youtube.com/embed/ALbScLraREg", // Network Security in 2 mins
                "https://www.youtube.com/embed/rG02r5y2Fdo", // Network Security Basics
        });

        videoDatabase.put("cloud", new String[] {
                "https://www.youtube.com/embed/F0vV9Iitf2Q", // What is Cloud Security
                "https://www.youtube.com/embed/M9p7mH6K0oQ", // Cloud Security in 5 mins
        });

        videoDatabase.put("auth", new String[] {
                "https://www.youtube.com/embed/9JPnN1Z_iSY", // Authentication Explained
                "https://www.youtube.com/embed/UBUNrFtufWo", // Session vs Token
                "https://www.youtube.com/embed/BoyeFozmAXk" // Password Security
        });

        videoDatabase.put("general", new String[] {
                // Cyber Security In 7 Minutes
                "https://www.youtube.com/embed/inWWhr5tnEA",
                // What is Cyber Security?
                "https://www.youtube.com/embed/sdpxddDzXfE"
        });
    }
}
