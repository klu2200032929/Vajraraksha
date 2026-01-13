package com.vajraraksha.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "quizzes")
public class Quiz {
    @Id
    private String id;
    private String title;
    private String lessonId; // Link to a specific lesson (optional)
    private List<Question> questions;
    private int totalPoints;
}
