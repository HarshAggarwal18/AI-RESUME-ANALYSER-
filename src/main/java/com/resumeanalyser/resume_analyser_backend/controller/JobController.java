package com.resumeanalyser.resume_analyser_backend.controller;

import com.resumeanalyser.resume_analyser_backend.model.Job;
import com.resumeanalyser.resume_analyser_backend.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
    @Autowired
    private JobService jobService;

//    public JobController(JobService jobService) {
//        this.jobService = jobService;
//    }

    @GetMapping
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }
}
