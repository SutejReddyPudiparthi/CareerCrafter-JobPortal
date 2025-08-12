package com.hexaware.careercrafter.controller;

import com.hexaware.careercrafter.dto.JobSeekerDTO;
import com.hexaware.careercrafter.service.IJobSeekerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobseekers")
public class JobSeekerController {

    @Autowired
    private IJobSeekerService jobSeekerService;

    @PostMapping
    public ResponseEntity<JobSeekerDTO> createJobSeeker(@Valid @RequestBody JobSeekerDTO dto) {
        return new ResponseEntity<>(jobSeekerService.createJobSeeker(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobSeekerDTO> getJobSeekerById(@PathVariable int id) {
        return ResponseEntity.ok(jobSeekerService.getJobSeekerById(id));
    }

    @GetMapping
    public ResponseEntity<List<JobSeekerDTO>> getAllJobSeekers() {
        return ResponseEntity.ok(jobSeekerService.getAllJobSeekers());
    }

    @PutMapping
    public ResponseEntity<JobSeekerDTO> updateJobSeeker(@Valid @RequestBody JobSeekerDTO dto) {
        return ResponseEntity.ok(jobSeekerService.updateJobSeeker(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJobSeeker(@PathVariable int id) {
        jobSeekerService.deleteJobSeeker(id);
        return ResponseEntity.ok("Job Seeker deleted successfully");
    }
}
