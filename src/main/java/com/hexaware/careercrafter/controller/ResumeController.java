package com.hexaware.careercrafter.controller;

import com.hexaware.careercrafter.dto.ResumeDTO;
import com.hexaware.careercrafter.service.IResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {

    @Autowired
    private IResumeService resumeService;

    @PostMapping
    public ResumeDTO uploadResume(@RequestBody ResumeDTO resumeDTO) {
        return resumeService.uploadResume(resumeDTO);
    }

    @GetMapping("/{id}")
    public ResumeDTO getResumeById(@PathVariable int id) {
        return resumeService.getResumeById(id);
    }

    @GetMapping("/jobseeker/{jobSeekerId}")
    public List<ResumeDTO> getResumesByJobSeekerId(@PathVariable int jobSeekerId) {
        return resumeService.getResumesByJobSeekerId(jobSeekerId);
    }

    @PutMapping
    public ResumeDTO updateResume(@RequestBody ResumeDTO resumeDTO) {
        return resumeService.updateResume(resumeDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteResume(@PathVariable int id) {
        resumeService.deleteResume(id);
    }
}
