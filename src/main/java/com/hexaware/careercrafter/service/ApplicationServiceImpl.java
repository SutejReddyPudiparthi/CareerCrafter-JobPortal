package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.dto.ApplicationDTO;
import com.hexaware.careercrafter.entities.Application;
import com.hexaware.careercrafter.entities.Application.ApplicationStatus;
import com.hexaware.careercrafter.entities.JobListing;
import com.hexaware.careercrafter.entities.JobSeeker;
import com.hexaware.careercrafter.exception.InvalidRequestException;
import com.hexaware.careercrafter.exception.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.ApplicationRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/*
 * Implementation of IApplicationService.
 * Implements application-related operations.
 */

@Service
public class ApplicationServiceImpl implements IApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationServiceImpl.class);

    @Autowired
    private ApplicationRepository applicationRepository;

    @Override
    public ApplicationDTO applyForJob(ApplicationDTO dto) {
        logger.debug("Applying for job ID {} by job seeker ID {}", dto.getJobListingId(), dto.getJobSeekerId());
        if (dto.getJobListingId() == 0 || dto.getJobSeekerId() == 0) {
            logger.error("Missing jobListingId or jobSeekerId");
            throw new InvalidRequestException("jobListingId and jobSeekerId are required");
        }
        Application entity = mapToEntity(dto);
        Application saved = applicationRepository.save(entity);
        logger.info("Application created with ID {}", saved.getApplicationId());
        return mapToDTO(saved);
    }

    @Override
    public ApplicationDTO getApplicationById(int id) {
        Application entity = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id " + id));
        return mapToDTO(entity);
    }

    @Override
    public List<ApplicationDTO> getAllApplications() {
        List<Application> entities = applicationRepository.findAll();
        List<ApplicationDTO> dtos = new ArrayList<>();
        for (Application e : entities) {
            dtos.add(mapToDTO(e));
        }
        return dtos;
    }

    @Override
    public List<ApplicationDTO> getApplicationsByJobSeekerId(int seekerId) {
        List<Application> entities = applicationRepository.findByJobSeekerJobSeekerId(seekerId);
        List<ApplicationDTO> dtos = new ArrayList<>();
        for (Application e : entities) {
            dtos.add(mapToDTO(e));
        }
        return dtos;
    }

    @Override
    public ApplicationDTO updateApplication(ApplicationDTO dto) {
        if (!applicationRepository.existsById(dto.getApplicationId())) {
            throw new ResourceNotFoundException("Application not found with id " + dto.getApplicationId());
        }
        Application updatedEntity = mapToEntity(dto);
        Application saved = applicationRepository.save(updatedEntity);
        return mapToDTO(saved);
    }

    @Override
    public void deleteApplication(int id) {
        if (!applicationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Application not found with id " + id);
        }
        applicationRepository.deleteById(id);
    }

    private ApplicationDTO mapToDTO(Application entity) {
        ApplicationDTO dto = new ApplicationDTO();
        dto.setApplicationId(entity.getApplicationId());
        dto.setJobSeekerId(entity.getJobSeeker() != null ? entity.getJobSeeker().getJobSeekerId() : 0);
        dto.setStatus(ApplicationDTO.ApplicationStatus.valueOf(entity.getStatus().name()));
        dto.setResumeFilePath(entity.getResumeFilePath());
        return dto;
    }

    private Application mapToEntity(ApplicationDTO dto) {
        Application entity = new Application();
        entity.setApplicationId(dto.getApplicationId());
        JobListing jobListing = new JobListing();
        entity.setJobListing(jobListing);
        JobSeeker jobSeeker = new JobSeeker();
        jobSeeker.setJobSeekerId(dto.getJobSeekerId());
        entity.setJobSeeker(jobSeeker);
        entity.setStatus(ApplicationStatus.valueOf(dto.getStatus().name()));
        entity.setResumeFilePath(dto.getResumeFilePath());
        return entity;
    }
}
