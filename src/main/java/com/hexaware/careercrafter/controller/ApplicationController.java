package com.hexaware.careercrafter.controller;

import com.hexaware.careercrafter.dto.ApplicationDTO;
import com.hexaware.careercrafter.service.IApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@Tag(name = "Applications", description = "Application management APIs")
public class ApplicationController {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationController.class);

    @Autowired
    private IApplicationService applicationService;

    @PreAuthorize("hasRole('JOBSEEKER')")
    @Operation(summary = "Apply for a job")
    @PostMapping
    public ApplicationDTO applyForJob(@RequestBody ApplicationDTO applicationDTO) {
        logger.info("Job seeker {} applying for job listing {}", applicationDTO.getJobListingId(), applicationDTO.getJobSeekerId());
        return applicationService.applyForJob(applicationDTO);
    }

    @PreAuthorize("hasAnyRole('JOBSEEKER', 'EMPLOYER')")
    @Operation(summary = "Get application by ID")
    @GetMapping("/{id}")
    public ApplicationDTO getApplicationById(@PathVariable int id) {
        logger.info("Fetching application with ID: {}", id);
        return applicationService.getApplicationById(id);
    }

    @PreAuthorize("hasRole('EMPLOYER')")
    @Operation(summary = "Get all applications")
    @GetMapping
    public List<ApplicationDTO> getAllApplications() {
        logger.info("Fetching all applications");
        return applicationService.getAllApplications();
    }

    @PreAuthorize("hasRole('JOBSEEKER')")
    @Operation(summary = "Get applications by job seeker ID")
    @GetMapping("/jobseeker/{seekerId}")
    public List<ApplicationDTO> getApplicationsByJobSeekerId(@PathVariable int seekerId) {
        logger.info("Fetching applications for job seeker ID: {}", seekerId);
        return applicationService.getApplicationsByJobSeekerId(seekerId);
    }

    @PreAuthorize("hasRole('EMPLOYER')")
    @Operation(summary = "Get applications by job listing ID (employer)")
    @GetMapping("/joblisting/{listingId}")
    public List<ApplicationDTO> getApplicationsByJobListingId(@PathVariable int listingId) {
        logger.info("Fetching applications for job listing ID: {}", listingId);
        return applicationService.getApplicationsByJobListingId(listingId);
    }

    @PreAuthorize("hasRole('JOBSEEKER') or hasRole('EMPLOYER')")
    @Operation(summary = "Update application details")
    @PutMapping
    public ApplicationDTO updateApplication(@RequestBody ApplicationDTO applicationDTO) {
        logger.info("Updating application with ID: {}", applicationDTO.getApplicationId());
        return applicationService.updateApplication(applicationDTO);
    }

    @PreAuthorize("hasRole('JOBSEEKER') or hasRole('EMPLOYER')")
    @Operation(summary = "Delete an application")
    @DeleteMapping("/{id}")
    public void deleteApplication(@PathVariable int id) {
        logger.info("Deleting application with ID: {}", id);
        applicationService.deleteApplication(id);
    }

}
