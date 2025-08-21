package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.dto.JobListingDTO;

import org.springframework.data.domain.Page;

import java.util.List;

/*
 * service interface specifying operations for job listing profiles.
 */

public interface IJobListingService {

    JobListingDTO createJobListing(JobListingDTO jobListingDTO);
    JobListingDTO getJobListingById(int id);
    List<JobListingDTO> getAll();
    List<JobListingDTO> getActiveJobListings();
    List<JobListingDTO> getJobListingsByEmployerId(int employerId);
    JobListingDTO updateJobListing(JobListingDTO jobListingDTO);
    void deleteJobListing(int id);
    
    Page<JobListingDTO> getJobListings(int page, int size, String sortBy, String sortDir);
    Page<JobListingDTO> advancedSearch(String location, String jobType, String qualification,
                                       int page, int size, String sortBy, String sortDir);
}
