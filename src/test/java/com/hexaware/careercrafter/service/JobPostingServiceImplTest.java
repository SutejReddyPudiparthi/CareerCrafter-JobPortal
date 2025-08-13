package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.dto.JobPostingDTO;
import com.hexaware.careercrafter.entities.JobPosting;
import com.hexaware.careercrafter.exception.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.JobPostingRepository;
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
class JobPostingServiceImplTest {

    @Mock JobPostingRepository repo;
    @InjectMocks JobPostingServiceImpl service;

    private JobPostingDTO dto;
    private JobPosting entity;

    @BeforeEach
    void setup() {
        dto = new JobPostingDTO();
        dto.setJobPostingId(1);
        dto.setEmployerId(2);
        dto.setTitle("Dev");
        dto.setDescription("Java Dev");

        entity = new JobPosting(); entity.setJobPostingId(1);
    }

    @Test
    void createJobPosting_success() {
        when(repo.save(any())).thenReturn(entity);
        assertNotNull(service.createJobPosting(dto));
    }

    @Test
    void getJobPostingById_found() {
        when(repo.findById(1)).thenReturn(Optional.of(entity));
        assertNotNull(service.getJobPostingById(1));
    }

    @Test
    void getJobPostingById_notFound() {
        when(repo.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getJobPostingById(1));
    }

    @Test
    void deleteJobPosting_notFound() {
        when(repo.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.deleteJobPosting(1));
    }
}

