package com.vajraraksha.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;
import lombok.Data;

@Data
@Document(collection = "courses")
public class Course {
    @Id
    private String id;
    private String title;
    private String description;
    private String level; // Beginner, Intermediate, Advanced
    private String instructorId;
    
    // Simplified structure for demo
    private List<Lesson> lessons;
}
