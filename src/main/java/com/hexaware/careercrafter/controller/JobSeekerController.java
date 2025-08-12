package com.hexaware.careercrafter.controller;

import com.hexaware.careercrafter.dto.JobSeekerDTO;
import com.hexaware.careercrafter.service.IJobSeekerService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobseekers")
public class JobSeekerController {

    private static final Logger logger = LoggerFactory.getLogger(JobSeekerController.class);

    @Autowired
    private IJobSeekerService jobSeekerService;

    @PostMapping
    public ResponseEntity<JobSeekerDTO> createJobSeeker(@Valid @RequestBody JobSeekerDTO dto) {
        logger.info("Request to create job seeker for userId: {}", dto.getUserId());
        JobSeekerDTO created = jobSeekerService.createJobSeeker(dto);
        logger.info("Job seeker created successfully with ID: {}", created.getJobSeekerId());
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobSeekerDTO> getJobSeekerById(@PathVariable int id) {
        logger.info("Request to fetch job seeker with ID: {}", id);
        JobSeekerDTO seeker = jobSeekerService.getJobSeekerById(id);
        logger.info("Successfully fetched job seeker with ID: {}", id);
        return ResponseEntity.ok(seeker);
    }

    @GetMapping
    public ResponseEntity<List<JobSeekerDTO>> getAllJobSeekers() {
        logger.info("Request to fetch all job seekers");
        List<JobSeekerDTO> seekers = jobSeekerService.getAllJobSeekers();
        logger.info("Successfully fetched {} job seekers", seekers.size());
        return ResponseEntity.ok(seekers);
    }

    @PutMapping
    public ResponseEntity<JobSeekerDTO> updateJobSeeker(@Valid @RequestBody JobSeekerDTO dto) {
        logger.info("Request to update job seeker with ID: {}", dto.getJobSeekerId());
        JobSeekerDTO updated = jobSeekerService.updateJobSeeker(dto);
        logger.info("Job seeker with ID {} updated successfully", dto.getJobSeekerId());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJobSeeker(@PathVariable int id) {
        logger.info("Request to delete job seeker with ID: {}", id);
        jobSeekerService.deleteJobSeeker(id);
        logger.info("Job seeker with ID {} deleted successfully", id);
        return ResponseEntity.ok("Job Seeker deleted successfully");
    }
}
