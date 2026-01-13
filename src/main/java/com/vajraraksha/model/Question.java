package com.vajraraksha.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    private String questionText;
    private List<String> options;
    private int correctOptionIndex; // 0-based index
    private String explanation;

    // Convenience constructor for AI Generator
    public Question(String questionText, List<String> options, String correctAnswerText) {
        this.questionText = questionText;
        this.options = options;
        this.correctOptionIndex = options.indexOf(correctAnswerText);
        if (this.correctOptionIndex == -1)
            this.correctOptionIndex = 0; // Fallback
        this.explanation = "Correct answer: " + correctAnswerText;
    }
}
