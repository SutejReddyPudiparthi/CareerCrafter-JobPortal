package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.dto.ApplicationDTO;
import com.hexaware.careercrafter.entities.Application;
import com.hexaware.careercrafter.exception.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.ApplicationRepository;
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
class ApplicationServiceImplTest {

    @Mock ApplicationRepository repo;
    @InjectMocks ApplicationServiceImpl service;

    private ApplicationDTO dto;
    private Application entity;

    @BeforeEach
    void setup() {
        dto = new ApplicationDTO();
        dto.setApplicationId(1);
        dto.setJobListingId(2);
        dto.setJobSeekerId(3);
        dto.setStatus(ApplicationDTO.ApplicationStatus.APPLIED);

        entity = new Application();
        entity.setApplicationId(1);
        entity.setStatus(Application.ApplicationStatus.APPLIED);
    }

    @Test
    void applyForJob_success() {
        when(repo.save(any())).thenReturn(entity);
        assertNotNull(service.applyForJob(dto));
    }

    @Test
    void getApplicationById_found() {
        when(repo.findById(1)).thenReturn(Optional.of(entity));
        assertNotNull(service.getApplicationById(1));
    }

    @Test
    void getApplicationById_notFound() {
        when(repo.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getApplicationById(1));
    }

    @Test
    void deleteApplication_notFound() {
        when(repo.existsById(1)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> service.deleteApplication(1));
    }
}

