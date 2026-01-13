package com.vajraraksha.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {
    private String title;
    private String content; // Text content or video URL
    private String videoUrl;
}
