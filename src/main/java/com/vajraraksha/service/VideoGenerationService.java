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
    public String getVideoForTopic(String topic) {
        String lowerTopic = topic.toLowerCase();

        // 1. Broadened Keyword Matching
        if (lowerTopic.contains("sql") || lowerTopic.contains("database") || lowerTopic.contains("injection")) {
            return selectRandom(videoDatabase.get("sqli"));
        } else if (lowerTopic.contains("xss") || lowerTopic.contains("cross-site")
                || lowerTopic.contains("scripting")) {
            return selectRandom(videoDatabase.get("xss"));
        } else if (lowerTopic.contains("phishing") || lowerTopic.contains("email") || lowerTopic.contains("scam")
                || lowerTopic.contains("social engineering")) {
            return selectRandom(videoDatabase.get("phishing"));
        } else if (lowerTopic.contains("malware") || lowerTopic.contains("virus") || lowerTopic.contains("ransomware")
                || lowerTopic.contains("trojan") || lowerTopic.contains("spyware")) {
            return selectRandom(videoDatabase.get("malware"));
        } else if (lowerTopic.contains("cryptography") || lowerTopic.contains("encryption")
                || lowerTopic.contains("hashing") || lowerTopic.contains("decryption")) {
            return selectRandom(videoDatabase.get("crypto"));
        } else if (lowerTopic.contains("firewall") || lowerTopic.contains("network") || lowerTopic.contains("osi")
                || lowerTopic.contains("tcp") || lowerTopic.contains("ip") || lowerTopic.contains("port")) {
            return selectRandom(videoDatabase.get("network"));
        } else if (lowerTopic.contains("cloud") || lowerTopic.contains("aws") || lowerTopic.contains("azure")
                || lowerTopic.contains("gcp")) {
            return selectRandom(videoDatabase.get("cloud"));
        } else if (lowerTopic.contains("password") || lowerTopic.contains("authentication")
                || lowerTopic.contains("login") || lowerTopic.contains("identity") || lowerTopic.contains("access")) {
            return selectRandom(videoDatabase.get("auth"));
        } else if (lowerTopic.contains("scan") || lowerTopic.contains("vulnerability") || lowerTopic.contains("recon")
                || lowerTopic.contains("nmap")) {
            return selectRandom(videoDatabase.get("scanning"));
        } else if (lowerTopic.contains("iot") || lowerTopic.contains("internet of things")
                || lowerTopic.contains("device")) {
            return selectRandom(videoDatabase.get("iot"));
        }

        // 2. Fallback "Generative" Selection (General Tech/Security Visualization)
        // In a real system, this would call a Text-to-Video API.
        // Here we return a generic, high-tech security background or intro.
        return selectRandom(videoDatabase.get("general"));
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

        // New Categories
        videoDatabase.put("scanning", new String[] {
                "https://www.youtube.com/embed/HkZ8OW1X6Qk", // Nmap (Network Scanning)
                "https://www.youtube.com/embed/4gR562GW7TI" // Vulnerability Scanning Context
        });

        videoDatabase.put("iot", new String[] {
                "https://www.youtube.com/embed/QSIPNhOiMoE", // IoT Security Explained
        });

        videoDatabase.put("general", new String[] {
                // Cyber Security In 7 Minutes (Simplilearn - generally safe)
                "https://www.youtube.com/embed/inWWhr5tnEA",
                // What is Cyber Security? (Konnect)
                "https://www.youtube.com/embed/sdpxddDzXfE",
                // IBM Technology - What is Cybersecurity? (Very Safe)
                "https://www.youtube.com/embed/0GjsOQG9iX0"
        });
    }
}
