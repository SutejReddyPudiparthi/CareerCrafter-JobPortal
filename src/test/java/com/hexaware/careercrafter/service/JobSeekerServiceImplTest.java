package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.dto.JobSeekerDTO;
import com.hexaware.careercrafter.entities.JobSeeker;
import com.hexaware.careercrafter.entities.User;
import com.hexaware.careercrafter.exception.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.JobSeekerRepository;
import com.hexaware.careercrafter.repository.UserRepository;
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
class JobSeekerServiceImplTest {

    @Mock JobSeekerRepository jobSeekerRepository;
    @Mock UserRepository userRepository;
    @InjectMocks JobSeekerServiceImpl service;

    private JobSeekerDTO dto;
    private JobSeeker entity;
    private User user;

    @BeforeEach
    void setup() {
        dto = new JobSeekerDTO();
        dto.setJobSeekerId(1);
        dto.setUserId(2);
        dto.setFullName("Alice Smith");

        user = new User(); user.setUserId(2);

        entity = new JobSeeker();
        entity.setJobSeekerId(1);
        entity.setUser(user);
    }

    @Test
    void createJobSeeker_success() {
        when(userRepository.findById(2)).thenReturn(Optional.of(user));
        when(jobSeekerRepository.save(any())).thenReturn(entity);
        assertNotNull(service.createJobSeeker(dto));
    }

    @Test
    void getJobSeekerById_found() {
        when(jobSeekerRepository.findById(1)).thenReturn(Optional.of(entity));
        assertNotNull(service.getJobSeekerById(1));
    }

    @Test
    void getJobSeekerById_notFound() {
        when(jobSeekerRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getJobSeekerById(1));
    }

    @Test
    void deleteJobSeeker_notFound() {
        when(jobSeekerRepository.existsById(1)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> service.deleteJobSeeker(1));
    }
}
