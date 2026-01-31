package com.resumeanalyser.resume_analyser_backend.service;

import com.resumeanalyser.resume_analyser_backend.model.Job;
import com.resumeanalyser.resume_analyser_backend.repository.JobRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }
}
