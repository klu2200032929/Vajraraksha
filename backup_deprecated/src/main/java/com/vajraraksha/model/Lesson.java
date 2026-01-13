package com.vajraraksha.model;

import lombok.Data;

@Data
public class Lesson {
    private String title;
    private String content; // Could be HTML or link to video
    private String videoUrl;
}
