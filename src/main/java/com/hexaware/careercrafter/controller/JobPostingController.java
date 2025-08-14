package com.hexaware.careercrafter.controller;

import com.hexaware.careercrafter.dto.JobPostingDTO;
import com.hexaware.careercrafter.service.IJobPostingService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * Rest Controller for Job posting management.
 */

@RestController
@RequestMapping("/api/jobpostings")
public class JobPostingController {

    private static final Logger logger = LoggerFactory.getLogger(JobPostingController.class);

    @Autowired
    private IJobPostingService jobPostingService;

    @PostMapping
    public ResponseEntity<JobPostingDTO> createJobPosting(@Valid @RequestBody JobPostingDTO dto) {
        logger.info("Request to create job posting with title: {}", dto.getTitle());
        JobPostingDTO created = jobPostingService.createJobPosting(dto);
        logger.info("Job posting created successfully with ID: {}", created.getJobPostingId());
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobPostingDTO> getJobPostingById(@PathVariable int id) {
        logger.info("Request to fetch job posting with ID: {}", id);
        JobPostingDTO posting = jobPostingService.getJobPostingById(id);
        logger.info("Successfully fetched job posting with ID: {}", id);
        return ResponseEntity.ok(posting);
    }

    @GetMapping
    public ResponseEntity<List<JobPostingDTO>> getAllJobPostings() {
        logger.info("Request to fetch all job postings");
        List<JobPostingDTO> postings = jobPostingService.getAllJobPostings();
        logger.info("Fetched {} job postings", postings.size());
        return ResponseEntity.ok(postings);
    }

    @PutMapping
    public ResponseEntity<JobPostingDTO> updateJobPosting(@Valid @RequestBody JobPostingDTO dto) {
        logger.info("Request to update job posting with ID: {}", dto.getJobPostingId());
        JobPostingDTO updated = jobPostingService.updateJobPosting(dto);
        logger.info("Job posting with ID {} updated successfully", dto.getJobPostingId());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJobPosting(@PathVariable int id) {
        logger.info("Request to delete job posting with ID: {}", id);
        jobPostingService.deleteJobPosting(id);
        logger.info("Job posting with ID {} deleted successfully", id);
        return ResponseEntity.ok("Job Posting deleted successfully");
    }
}
