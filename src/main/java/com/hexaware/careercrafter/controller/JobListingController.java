package com.hexaware.careercrafter.controller;

import com.hexaware.careercrafter.dto.JobListingDTO;
import com.hexaware.careercrafter.service.IJobListingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/joblistings")
@PreAuthorize("hasRole('EMPLOYER')")
@Tag(name = "Job Listings", description = "Job listing management APIs")
public class JobListingController {

    private static final Logger logger = LoggerFactory.getLogger(JobListingController.class);

    @Autowired
    private IJobListingService jobListingService;

    @Operation(summary = "Create a job listing")
    @PostMapping
    public ResponseEntity<JobListingDTO> createJobListing(@Valid @RequestBody JobListingDTO dto) {
        logger.info("Request to create job listing with title: {}", dto.getTitle());
        JobListingDTO created = jobListingService.createJobListing(dto);
        logger.info("Job listing created successfully with ID: {}", created.getJobListingId());
        return ResponseEntity.status(201).body(created);
    }

    @Operation(summary = "Get job listing by ID")
    @GetMapping("/{id}")
    public ResponseEntity<JobListingDTO> getJobListingById(@PathVariable int id) {
        logger.info("Request to fetch job listing with ID: {}", id);
        JobListingDTO listing = jobListingService.getJobListingById(id);
        logger.info("Successfully fetched job listing with ID: {}", id);
        return ResponseEntity.ok(listing);
    }

    @Operation(summary = "Get paginated and sorted job listings")
    @GetMapping
    public ResponseEntity<Page<JobListingDTO>> getJobListings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "postedDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        logger.info("Fetching job listings page: {}, size: {}, sortBy: {}, sortDir: {}", page, size, sortBy, sortDir);
        Page<JobListingDTO> listings = jobListingService.getJobListings(page, size, sortBy, sortDir);
        return ResponseEntity.ok(listings);
    }

    @Operation(summary = "Update a job listing")
    @PutMapping
    public ResponseEntity<JobListingDTO> updateJoListing(@Valid @RequestBody JobListingDTO dto) {
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

    @Operation(summary = "Advanced search for job listings with filters, pagination, and sorting")
    @GetMapping("/search")
    public ResponseEntity<Page<JobListingDTO>> searchJobListings(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String jobType,
            @RequestParam(required = false) String qualification,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "postedDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        logger.info("Searching job listings with filters location: {}, jobType: {}, qualification: {}, page: {}, size: {}, sortBy: {}, sortDir: {}",
                location, jobType, qualification, page, size, sortBy, sortDir);

        Page<JobListingDTO> results = jobListingService.advancedSearch(location, jobType, qualification, page, size, sortBy, sortDir);
        return ResponseEntity.ok(results);
    }

}
