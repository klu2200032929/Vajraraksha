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
@Document(collection = "courses")
public class Course {
    @Id
    private String id;

    private String title;
    private String description;
    private String instructorId; // ID of the instructor who created it

    private List<Lesson> lessons = new ArrayList<>();
}
