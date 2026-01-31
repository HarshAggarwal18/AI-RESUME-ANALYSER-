package com.resumeanalyser.resume_analyser_backend.repository;

import com.resumeanalyser.resume_analyser_backend.model.Job;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends MongoRepository<Job, Long> {
}
