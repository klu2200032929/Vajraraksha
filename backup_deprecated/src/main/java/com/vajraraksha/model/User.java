package com.vajraraksha.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;
import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    private String id;
    
    private String username;
    private String email;
    private String password; // Encrypted/Hashed
    
    private Role role;
    
    // Gamification
    private int points = 0;
    private List<String> badges = new ArrayList<>();
    
    // Progress Tracking
    private List<String> completedCourses = new ArrayList<>();
    private List<String> completedLabs = new ArrayList<>();
}
