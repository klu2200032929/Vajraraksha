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
    public String generateVideoForTopic(String topic) {
        String lowerTopic = topic.toLowerCase();

        // 1. Exact/High-Confidence Keyword Matching
        if (lowerTopic.contains("sql") && lowerTopic.contains("injection")) {
            return selectRandom(videoDatabase.get("sqli"));
        } else if (lowerTopic.contains("xss") || lowerTopic.contains("cross-site")) {
            return selectRandom(videoDatabase.get("xss"));
        } else if (lowerTopic.contains("phishing")) {
            return selectRandom(videoDatabase.get("phishing"));
        } else if (lowerTopic.contains("malware") || lowerTopic.contains("virus")
                || lowerTopic.contains("ransomware")) {
            return selectRandom(videoDatabase.get("malware"));
        } else if (lowerTopic.contains("cryptography") || lowerTopic.contains("encryption")) {
            return selectRandom(videoDatabase.get("crypto"));
        } else if (lowerTopic.contains("firewall") || lowerTopic.contains("network")) {
            return selectRandom(videoDatabase.get("network"));
        } else if (lowerTopic.contains("cloud")) {
            return selectRandom(videoDatabase.get("cloud"));
        } else if (lowerTopic.contains("password") || lowerTopic.contains("authentication")) {
            return selectRandom(videoDatabase.get("auth"));
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
                "https://www.youtube.com/embed/ciNHn38EyRc", // SQLi Explained
                "https://www.youtube.com/embed/_jKylhJtPmI", // Finding SQLi
                "https://www.youtube.com/embed/2Fp9B-3t6lQ" // Advanced SQLi
        });

        videoDatabase.put("xss", new String[] {
                "https://www.youtube.com/embed/EoaDgUgS6QA", // XSS Basics
                "https://www.youtube.com/embed/ns1LX6mEJfM", // Reflected vs Stored
                "https://www.youtube.com/embed/Mh7d8u_3GFE" // Bypassing XSS Filters
        });

        videoDatabase.put("phishing", new String[] {
                "https://www.youtube.com/embed/XBkzBrXll0A", // Phishing Analysis
                "https://www.youtube.com/embed/F7pYpn9NESI", // Social Engineering
                "https://www.youtube.com/embed/p1i4eK77F5M" // Spear Phishing
        });

        videoDatabase.put("malware", new String[] {
                "https://www.youtube.com/embed/n8kyX_wQdZs", // Malware Intro
                "https://www.youtube.com/embed/fTGTnrgjuGA", // Malware Analysis
                "https://www.youtube.com/embed/4gR562GW7TI" // Ransomware
        });

        videoDatabase.put("crypto", new String[] {
                "https://www.youtube.com/embed/ERp8420ucGs", // Encryption 101
                "https://www.youtube.com/embed/2BldESGZKB8", // Hashing
                "https://www.youtube.com/embed/GSIDS_lvRv4" // Public Key Infrastructure
        });

        videoDatabase.put("network", new String[] {
                "https://www.youtube.com/embed/3u7_8qM0tgo", // OSI Model
                "https://www.youtube.com/embed/HkZ8OW1X6Qk", // Nmap
                "https://www.youtube.com/embed/TkCSr30UojM" // Wireshark
        });

        videoDatabase.put("cloud", new String[] {
                "https://www.youtube.com/embed/YO_O_mXV_Yw", // Cloud Security
                "https://www.youtube.com/embed/i_JUj8335dc" // AWS Security
        });

        videoDatabase.put("auth", new String[] {
                "https://www.youtube.com/embed/H4Y87yP354k", // Password Hashing
                "https://www.youtube.com/embed/15w521925rQ" // MFA
        });

        videoDatabase.put("general", new String[] {
                // Using a tech-themed public MP4 to simulate "AI Generated" content
                // In a real app, this would be the URL of the file created by the Gen-AI model
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4"
        });
    }
}
