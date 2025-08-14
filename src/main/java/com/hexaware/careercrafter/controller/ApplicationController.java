package com.hexaware.careercrafter.controller;

import com.hexaware.careercrafter.dto.ApplicationDTO;
import com.hexaware.careercrafter.service.IApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/*
 * Rest Controller for job applications.
 * Manages the applications.
 */

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationController.class);

    @Autowired
    private IApplicationService applicationService;

    @PostMapping
    public ApplicationDTO applyForJob(@RequestBody ApplicationDTO applicationDTO) {
        logger.info("Request to apply for job ID: {} by job seeker ID: {}", applicationDTO.getJobPostingId(), applicationDTO.getJobSeekerId());
        ApplicationDTO created = applicationService.applyForJob(applicationDTO);
        logger.info("Application created successfully with ID: {}", created.getApplicationId());
        return created;
    }

    @GetMapping("/{id}")
    public ApplicationDTO getApplicationById(@PathVariable int id) {
        logger.info("Request to fetch application with ID: {}", id);
        ApplicationDTO dto = applicationService.getApplicationById(id);
        logger.info("Fetched application with ID: {}", id);
        return dto;
    }

    @GetMapping
    public List<ApplicationDTO> getAllApplications() {
        logger.info("Request to fetch all applications");
        List<ApplicationDTO> list = applicationService.getAllApplications();
        logger.info("Fetched {} applications", list.size());
        return list;
    }

    @GetMapping("/jobseeker/{seekerId}")
    public List<ApplicationDTO> getApplicationsByJobSeekerId(@PathVariable int seekerId) {
        logger.info("Request to fetch applications for job seeker ID: {}", seekerId);
        List<ApplicationDTO> list = applicationService.getApplicationsByJobSeekerId(seekerId);
        logger.info("Fetched {} applications for job seeker ID: {}", list.size(), seekerId);
        return list;
    }

    @PutMapping
    public ApplicationDTO updateApplication(@RequestBody ApplicationDTO applicationDTO) {
        logger.info("Request to update application with ID: {}", applicationDTO.getApplicationId());
        ApplicationDTO updated = applicationService.updateApplication(applicationDTO);
        logger.info("Application with ID {} updated successfully", applicationDTO.getApplicationId());
        return updated;
    }

    @DeleteMapping("/{id}")
    public void deleteApplication(@PathVariable int id) {
        logger.info("Request to delete application with ID: {}", id);
        applicationService.deleteApplication(id);
        logger.info("Application with ID {} deleted successfully", id);
    }
}
