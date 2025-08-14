package com.hexaware.careercrafter.controller;

import com.hexaware.careercrafter.dto.ResumeDTO;
import com.hexaware.careercrafter.service.IResumeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/*
 * Rest Controller for resume operations.
 * Handles resumes.
 */

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {

    private static final Logger logger = LoggerFactory.getLogger(ResumeController.class);

    @Autowired
    private IResumeService resumeService;

    @PostMapping
    public ResumeDTO uploadResume(@RequestBody ResumeDTO resumeDTO) {
        logger.info("Request to upload resume for jobSeekerId: {}", resumeDTO.getJobSeekerId());
        ResumeDTO created = resumeService.uploadResume(resumeDTO);
        logger.info("Resume uploaded successfully with ID: {}", created.getResumeId());
        return created;
    }

    @GetMapping("/{id}")
    public ResumeDTO getResumeById(@PathVariable int id) {
        logger.info("Request to fetch resume with ID: {}", id);
        ResumeDTO resume = resumeService.getResumeById(id);
        logger.info("Successfully fetched resume with ID: {}", id);
        return resume;
    }

    @GetMapping("/jobseeker/{jobSeekerId}")
    public List<ResumeDTO> getResumesByJobSeekerId(@PathVariable int jobSeekerId) {
        logger.info("Request to fetch resumes for jobSeekerId: {}", jobSeekerId);
        List<ResumeDTO> resumes = resumeService.getResumesByJobSeekerId(jobSeekerId);
        logger.info("Fetched {} resumes for jobSeekerId: {}", resumes.size(), jobSeekerId);
        return resumes;
    }

    @PutMapping
    public ResumeDTO updateResume(@RequestBody ResumeDTO resumeDTO) {
        logger.info("Request to update resume with ID: {}", resumeDTO.getResumeId());
        ResumeDTO updated = resumeService.updateResume(resumeDTO);
        logger.info("Resume with ID {} updated successfully", resumeDTO.getResumeId());
        return updated;
    }

    @DeleteMapping("/{id}")
    public void deleteResume(@PathVariable int id) {
        logger.info("Request to delete resume with ID: {}", id);
        resumeService.deleteResume(id);
        logger.info("Resume with ID {} deleted successfully", id);
    }
}
