package com.hexaware.careercrafter.dto;

import com.hexaware.careercrafter.entities.JobListing.JobType;

import jakarta.validation.constraints.*;

import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/*
 * Data transfer object for job listing details.
 * Used to transfer job listing data.
 * 
 */


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobListingDTO {

    private int jobListingId;

    @NotNull(message="EmployerId is required")
    private int employerId;

    @NotBlank(message="Job Title is required")
    @Size(min=3, max = 100, message = "Job Title must be between 3 and 100 characters")
    private String title;

    @NotBlank(message="Job Description is required")
    @Size(min=5, max = 500, message = "Job Description must be between 5 and 500 characters")
    private String description;
    
    @NotBlank(message = "Qualification are required")
    @Size(max = 200, message = "Qualification cannot exceed 200 characters")
    private String qualification;

    @NotBlank(message="Location is required")
    @Size(max = 200, message = "Location cannot exceed 200 characters")
    private String location;

    @NotNull(message="Job type is required")
    private JobType jobType;

    private boolean active;

}
