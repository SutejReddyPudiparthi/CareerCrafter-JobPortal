package com.hexaware.careercrafter.controller;

import com.hexaware.careercrafter.dto.JobPostingDTO;
import com.hexaware.careercrafter.service.IJobPostingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobpostings")
public class JobPostingController {

    @Autowired
    private IJobPostingService jobPostingService;

    @PostMapping
    public ResponseEntity<JobPostingDTO> createJobPosting(@Valid @RequestBody JobPostingDTO dto) {
        return new ResponseEntity<>(jobPostingService.createJobPosting(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobPostingDTO> getJobPostingById(@PathVariable int id) {
        return ResponseEntity.ok(jobPostingService.getJobPostingById(id));
    }

    @GetMapping
    public ResponseEntity<List<JobPostingDTO>> getAllJobPostings() {
        return ResponseEntity.ok(jobPostingService.getAllJobPostings());
    }

    @PutMapping
    public ResponseEntity<JobPostingDTO> updateJobPosting(@Valid @RequestBody JobPostingDTO dto) {
        return ResponseEntity.ok(jobPostingService.updateJobPosting(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJobPosting(@PathVariable int id) {
        jobPostingService.deleteJobPosting(id);
        return ResponseEntity.ok("Job Posting deleted successfully");
    }
}
