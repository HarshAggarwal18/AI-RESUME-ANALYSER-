package com.resumeanalyser.resume_analyser_backend.model;

import lombok.Data;

@Data
public class MatchScore {
    private double overall;
    private double skillsMatch;
    private double experienceMatch;
    private double educationMatch;
}
