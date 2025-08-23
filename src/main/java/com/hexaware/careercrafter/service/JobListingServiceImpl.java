package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.dto.JobListingDTO;
import com.hexaware.careercrafter.entities.Employer;
import com.hexaware.careercrafter.entities.JobListing;
import com.hexaware.careercrafter.exception.InvalidRequestException;
import com.hexaware.careercrafter.exception.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.JobListingRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/*
 * Implementation of IJobListingService.
 * Implements joblisting-related operations.
 */

@Service
public class JobListingServiceImpl implements IJobListingService {

    private static final Logger logger = LoggerFactory.getLogger(JobListingServiceImpl.class);

    @Autowired
    private JobListingRepository jobListingRepository;

    @Override
    public JobListingDTO createJobListing(JobListingDTO dto) {
        logger.debug("Creating job listing with employerId: {}, title: {}", dto.getEmployerId(), dto.getTitle());
        if (dto.getEmployerId() == 0 || dto.getTitle() == null || dto.getDescription() == null) {
            throw new InvalidRequestException("Employer, title, and description must be provided.");
        }
        JobListing entity = mapToEntity(dto);
        JobListing saved = jobListingRepository.save(entity);
        logger.info("Job listing created successfully with ID: {}", saved.getJobListingId());
        return mapToDTO(saved);
    }

    @Override
    public JobListingDTO getJobListingById(int id) {
        JobListing jobListing = jobListingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job listing not found with ID: " + id));
        return mapToDTO(jobListing);
    }

    @Override
    public List<JobListingDTO> getAll() {
        return jobListingRepository.findAll()
                .stream().map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public JobListingDTO updateJobListing(JobListingDTO dto) {
        if (!jobListingRepository.existsById(dto.getJobListingId())) {
            throw new ResourceNotFoundException("Job listing not found with ID: " + dto.getJobListingId());
        }
        JobListing entity = mapToEntity(dto);
        JobListing saved = jobListingRepository.save(entity);
        return mapToDTO(saved);
    }

    @Override
    public void deleteJobListing(int id) {
        JobListing jobListing = jobListingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job listing not found with ID: " + id));
        jobListing.setActive(false);
        jobListingRepository.save(jobListing);
    }

    @Override
    public List<JobListingDTO> getActiveJobListings() {
        return jobListingRepository.findByActiveTrue()
                .stream().map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<JobListingDTO> getJobListingsByEmployerId(int employerId) {
        return jobListingRepository.findByEmployerEmployerId(employerId)
                .stream().map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private JobListingDTO mapToDTO(JobListing entity) {
        JobListingDTO dto = new JobListingDTO();
        dto.setJobListingId(entity.getJobListingId());
        dto.setEmployerId(entity.getEmployer() != null ? entity.getEmployer().getEmployerId() : 0);
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setQualification(entity.getQualification());
        dto.setLocation(entity.getLocation());
        dto.setJobType(entity.getJobType());
        dto.setActive(entity.isActive());
        return dto;
    }

    private JobListing mapToEntity(JobListingDTO dto) {
        JobListing entity = new JobListing();
        entity.setJobListingId(dto.getJobListingId());
        Employer employer = new Employer();
        employer.setEmployerId(dto.getEmployerId());
        entity.setEmployer(employer);
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setQualification(dto.getQualification());
        entity.setLocation(dto.getLocation());
        entity.setJobType(dto.getJobType());
        entity.setActive(dto.isActive());
        return entity;
    }
}
