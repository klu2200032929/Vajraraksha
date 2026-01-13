package com.vajraraksha.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "labs")
public class Lab {
    @Id
    private String id;
    private String title;
    private String description;
    private String flag; // Capture the flag answer
    private int pointsAwarded;
    private String difficulty;
}
