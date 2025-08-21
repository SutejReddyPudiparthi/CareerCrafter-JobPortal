package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.dto.JobListingDTO;
import com.hexaware.careercrafter.entities.JobListing;
import com.hexaware.careercrafter.exception.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.JobListingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobListingServiceImplTest {

    @Mock JobListingRepository repo;
    @InjectMocks JobListingServiceImpl service;

    private JobListingDTO dto;
    private JobListing entity;

    @BeforeEach
    void setup() {
        dto = new JobListingDTO();
        dto.setJobListingId(1);
        dto.setEmployerId(2);
        dto.setTitle("Dev");
        dto.setDescription("Java Dev");

        entity = new JobListing(); entity.setJobListingId(1);
    }

    @Test
    void createJobListing_success() {
        when(repo.save(any())).thenReturn(entity);
        assertNotNull(service.createJobListing(dto));
    }

    @Test
    void getJobListingById_found() {
        when(repo.findById(1)).thenReturn(Optional.of(entity));
        assertNotNull(service.getJobListingById(1));
    }

    @Test
    void getJobListingById_notFound() {
        when(repo.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getJobListingById(1));
    }

    @Test
    void deleteJobListing_notFound() {
        when(repo.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.deleteJobListing(1));
    }
}

