package com.resumeanalyser.resume_analyser_backend.model;

import lombok.Data;
import java.util.List;

@Data
public class AnalysisResult {
    private String title;
    private String company;
    private String location;
    private String employmentType;
    private MatchScore matchScore;
    private List<String> matchingSkills;
    private List<String> missingSkills;
    private String whyFit;
    private String growthAreas;
    private String summary;
}
