package com.vajraraksha.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabStep {
    private int stepNumber;
    private String instruction;
    private String command; // The expected command to type
    private String outputExpected; // Validation check (regex or string)
    private String hint;
}
