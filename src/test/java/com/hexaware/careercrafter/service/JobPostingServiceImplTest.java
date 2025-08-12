package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.dto.JobPostingDTO;
import com.hexaware.careercrafter.entities.JobPosting;
import com.hexaware.careercrafter.exception.InvalidRequestException;
import com.hexaware.careercrafter.exception.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.JobPostingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobPostingServiceImplTest {

    @Mock JobPostingRepository repo;
    @InjectMocks JobPostingServiceImpl service;

    JobPostingDTO dto;
    JobPosting entity;

    @BeforeEach
    void setup() {
        dto = new JobPostingDTO();
        dto.setJobPostingId(1);
        dto.setEmployerId(2);
        dto.setTitle("Dev");
        dto.setDescription("Java Dev");

        entity = new JobPosting();
        entity.setJobPostingId(1);
    }

    @Test
    void create_success() {
        when(repo.save(any())).thenReturn(entity);
        assertNotNull(service.createJobPosting(dto));
    }

    @Test
    void create_invalid_throws() {
        dto.setTitle(null);
        assertThrows(InvalidRequestException.class, () -> service.createJobPosting(dto));
    }

    @Test
    void getAll_returnsList() {
        when(repo.findAll()).thenReturn(Arrays.asList(entity));
        assertEquals(1, service.getAllJobPostings().size());
    }

    @Test
    void getById_found() {
        when(repo.findById(1)).thenReturn(Optional.of(entity));
        assertNotNull(service.getJobPostingById(1));
    }

    @Test
    void delete_found_softDelete() {
        when(repo.findById(1)).thenReturn(Optional.of(entity));
        service.deleteJobPosting(1);
        verify(repo).save(entity);
    }

    @Test
    void delete_notFound_throw() {
        when(repo.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.deleteJobPosting(1));
    }
}
