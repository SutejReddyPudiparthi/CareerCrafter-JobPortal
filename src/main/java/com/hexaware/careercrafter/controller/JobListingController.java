package com.hexaware.careercrafter.controller;

import com.hexaware.careercrafter.dto.JobListingDTO;
import com.hexaware.careercrafter.service.IJobListingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * Controller for job listings.
 */

@RestController
@RequestMapping("/api/joblistings")
@PreAuthorize("hasRole('EMPLOYER')")
@Tag(name = "Job Listings", description = "Job listing management APIs")
public class JobListingController {

    private static final Logger logger = LoggerFactory.getLogger(JobListingController.class);

    @Autowired
    private IJobListingService jobListingService;

    @Operation(summary = "Create a new job listing")
    @PostMapping
    public ResponseEntity<JobListingDTO> createJobListing(@Valid @RequestBody JobListingDTO dto) {
        logger.info("Request to create job listing with title: {}", dto.getTitle());
        JobListingDTO created = jobListingService.createJobListing(dto);
        logger.info("Job listing created successfully with ID: {}", created.getJobListingId());
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Operation(summary = "Get job listing by ID")
    @GetMapping("/{id}")
    public ResponseEntity<JobListingDTO> getJobListingById(@PathVariable int id) {
        logger.info("Request to fetch job listing with ID: {}", id);
        JobListingDTO listing = jobListingService.getJobListingById(id);
        logger.info("Successfully fetched job listing with ID: {}", id);
        return ResponseEntity.ok(listing);
    }

    @Operation(summary = "Get all job listings")
    @GetMapping
    public ResponseEntity<List<JobListingDTO>> getAllJobListings() {
        logger.info("Request to fetch all job listings");
        List<JobListingDTO> listings = jobListingService.getAll();
        logger.info("Fetched {} job listings", listings.size());
        return ResponseEntity.ok(listings);
    }

    @Operation(summary = "Update a job listing")
    @PutMapping
    public ResponseEntity<JobListingDTO> updateJobListing(@Valid @RequestBody JobListingDTO dto) {
        logger.info("Request to update job listing with ID: {}", dto.getJobListingId());
        JobListingDTO updated = jobListingService.updateJobListing(dto);
        logger.info("Job listing updated successfully with ID: {}", dto.getJobListingId());
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Delete (soft) a job listing")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJobListing(@PathVariable int id) {
        logger.info("Request to delete job listing with ID: {}", id);
        jobListingService.deleteJobListing(id);
        logger.info("Job listing marked inactive with ID: {}", id);
        return ResponseEntity.ok("Job Listing deleted successfully");
    }
}
