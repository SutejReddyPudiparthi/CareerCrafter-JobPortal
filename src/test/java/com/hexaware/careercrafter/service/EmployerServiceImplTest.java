package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.dto.EmployerDTO;
import com.hexaware.careercrafter.entities.Employer;
import com.hexaware.careercrafter.entities.User;
import com.hexaware.careercrafter.exception.InvalidRequestException;
import com.hexaware.careercrafter.exception.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.EmployerRepository;
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
class EmployerServiceImplTest {

    @Mock EmployerRepository employerRepository;
    @Mock UserRepository userRepository;
    @InjectMocks EmployerServiceImpl service;

    EmployerDTO dto;
    User user;
    Employer entity;

    @BeforeEach
    void setup() {
        dto = new EmployerDTO();
        dto.setEmployerId(1);
        dto.setUserId(2);
        dto.setCompanyName("ABC Ltd");

        user = new User();
        user.setUserId(2);

        entity = new Employer();
        entity.setEmployerId(1);
        entity.setUser(user);
    }

    @Test
    void create_success() {
        when(userRepository.findById(2)).thenReturn(Optional.of(user));
        when(employerRepository.save(any())).thenReturn(entity);
        assertNotNull(service.createEmployer(dto));
    }

    @Test
    void create_invalid_throws() {
        dto.setCompanyName(null);
        assertThrows(InvalidRequestException.class, () -> service.createEmployer(dto));
    }

    @Test
    void getById_found() {
        when(employerRepository.findById(1)).thenReturn(Optional.of(entity));
        assertNotNull(service.getEmployerById(1));
    }

    @Test
    void delete_exists() {
        when(employerRepository.existsById(1)).thenReturn(true);
        service.deleteEmployer(1);
        verify(employerRepository).deleteById(1);
    }
}
