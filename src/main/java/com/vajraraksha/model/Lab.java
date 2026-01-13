package com.vajraraksha.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "labs")
public class Lab {
    @Id
    private String id;

    private String title;
    private String description;
    private String difficulty; // Beginner, Intermediate, Advanced
    private String instructorId;
    private String courseId; // Link to parent course for cascading delete

    // In a real app, this would be a link to a containerized environment or
    // simulation
    private String simulationUrl;

    private java.util.List<LabStep> steps; // Guided steps for the lab
    private int points; // Points awarded for completion
}
