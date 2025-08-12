package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.dto.ApplicationDTO;
import com.hexaware.careercrafter.entities.Application;
import com.hexaware.careercrafter.entities.Application.ApplicationStatus;
import com.hexaware.careercrafter.exception.InvalidRequestException;
import com.hexaware.careercrafter.exception.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.ApplicationRepository;
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
class ApplicationServiceImplTest {

    @Mock ApplicationRepository repo;
    @InjectMocks ApplicationServiceImpl service;

    ApplicationDTO dto;
    Application entity;

    @BeforeEach
    void setup() {
        dto = new ApplicationDTO();
        dto.setApplicationId(1);
        dto.setJobPostingId(2);
        dto.setJobSeekerId(3);
        dto.setStatus(ApplicationDTO.ApplicationStatus.APPLIED);

        entity = new Application();
        entity.setApplicationId(1);
        entity.setStatus(ApplicationStatus.APPLIED);
    }

    @Test
    void apply_success() {
        when(repo.save(any())).thenReturn(entity);
        assertNotNull(service.applyForJob(dto));
    }

    @Test
    void apply_invalid() {
        dto.setJobPostingId(0);
        assertThrows(InvalidRequestException.class, () -> service.applyForJob(dto));
    }

    @Test
    void getById_found() {
        when(repo.findById(1)).thenReturn(Optional.of(entity));
        assertNotNull(service.getApplicationById(1));
    }

    @Test
    void update_notFound() {
        when(repo.existsById(1)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> service.updateApplication(dto));
    }

    @Test
    void delete_found() {
        when(repo.existsById(1)).thenReturn(true);
        service.deleteApplication(1);
        verify(repo).deleteById(1);
    }
}
