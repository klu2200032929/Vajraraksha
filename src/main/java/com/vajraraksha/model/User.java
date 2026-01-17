package com.vajraraksha.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    private String id;

    private String username;
    private String email;
    private String password;

    // Security - OTP
    private String otp;
    private java.time.LocalDateTime otpExpiry;

    private Role role;

    // Gamification
    private int points = 0;
    private List<String> badges = new ArrayList<>();

    // Progress Tracking
    private List<String> completedCourses = new ArrayList<>();
    private List<String> completedLabs = new ArrayList<>();
    private List<String> completedLessons = new ArrayList<>(); // Format: courseId_lessonIndex

    private java.time.LocalDateTime createdAt = java.time.LocalDateTime.now();
}
