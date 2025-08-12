package com.hexaware.careercrafter.controller;

import com.hexaware.careercrafter.dto.ApplicationDTO;
import com.hexaware.careercrafter.service.IApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    @Autowired
    private IApplicationService applicationService;

    @PostMapping
    public ApplicationDTO applyForJob(@RequestBody ApplicationDTO applicationDTO) {
        return applicationService.applyForJob(applicationDTO);
    }

    @GetMapping("/{id}")
    public ApplicationDTO getApplicationById(@PathVariable int id) {
        return applicationService.getApplicationById(id);
    }

    @GetMapping
    public List<ApplicationDTO> getAllApplications() {
        return applicationService.getAllApplications();
    }

    @GetMapping("/jobseeker/{seekerId}")
    public List<ApplicationDTO> getApplicationsByJobSeekerId(@PathVariable int seekerId) {
        return applicationService.getApplicationsByJobSeekerId(seekerId);
    }

    @PutMapping
    public ApplicationDTO updateApplication(@RequestBody ApplicationDTO applicationDTO) {
        return applicationService.updateApplication(applicationDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteApplication(@PathVariable int id) {
        applicationService.deleteApplication(id);
    }
}
