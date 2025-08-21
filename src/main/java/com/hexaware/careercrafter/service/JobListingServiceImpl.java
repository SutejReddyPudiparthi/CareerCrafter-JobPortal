package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.dto.JobListingDTO;
import com.hexaware.careercrafter.entities.Employer;
import com.hexaware.careercrafter.entities.JobListing;
import com.hexaware.careercrafter.entities.JobListing.JobType;
import com.hexaware.careercrafter.exception.InvalidRequestException;
import com.hexaware.careercrafter.exception.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.JobListingRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.*;

import java.util.ArrayList;
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
    public Page<JobListingDTO> getJobListings(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<JobListing> listingPage = jobListingRepository.findAll(pageable);
        return listingPage.map(this::mapToDTO);
    }

    @Override
    public Page<JobListingDTO> advancedSearch(String location, String jobTypeStr, String qualification,
                                              int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<JobListing> spec = (Root<JobListing> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (location != null && !location.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("location")), "%" + location.toLowerCase() + "%"));
            }
            if (jobTypeStr != null && !jobTypeStr.isEmpty()) {
                try {
                    JobType jobType = JobType.valueOf(jobTypeStr.toUpperCase());
                    predicates.add(cb.equal(root.get("jobType"), jobType));
                } catch (IllegalArgumentException e) {
                    throw new InvalidRequestException("Invalid job type: " + jobTypeStr);
                }
            }
            if (qualification != null && !qualification.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("qualification")), "%" + qualification.toLowerCase() + "%"));
            }
            predicates.add(cb.isTrue(root.get("active")));

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<JobListing> results = jobListingRepository.findAll(spec, pageable);
        return results.map(this::mapToDTO);
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
