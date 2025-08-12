package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.dto.UserDTO;
import com.hexaware.careercrafter.entities.User;
import com.hexaware.careercrafter.exception.DuplicateResourceException;
import com.hexaware.careercrafter.exception.InvalidRequestException;
import com.hexaware.careercrafter.exception.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.UserRepository;
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
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    private UserDTO dto;
    private User user;

    @BeforeEach
    void setup() {
        dto = new UserDTO();
        dto.setUserId(1);
        dto.setName("John");
        dto.setEmail("john@test.com");
        dto.setPassword("pwd");

        user = new User();
        user.setUserId(1);
        user.setName("John");
        user.setEmail("john@test.com");
        user.setPassword("pwd");
    }

    @Test
    void createUser_success() {
        when(userRepository.findByEmail("john@test.com")).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(user);

        assertNotNull(userService.createUser(dto));
    }

    @Test
    void createUser_duplicate_throws() {
        when(userRepository.findByEmail("john@test.com")).thenReturn(user);
        assertThrows(DuplicateResourceException.class, () -> userService.createUser(dto));
    }

    @Test
    void createUser_missingFields_throws() {
        dto.setName(null);
        assertThrows(InvalidRequestException.class, () -> userService.createUser(dto));
    }

    @Test
    void getAllUsers_returnsList() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        assertEquals(1, userService.getAllUsers().size());
    }

    @Test
    void getUserById_found() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        assertEquals("John", userService.getUserById(1).getName());
    }

    @Test
    void getUserById_notFound_throws() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(1));
    }

    @Test
    void deleteUser_exists() {
        when(userRepository.existsById(1)).thenReturn(true);
        userService.deleteUser(1);
        verify(userRepository).deleteById(1);
    }

    @Test
    void deleteUser_notFound_throws() {
        when(userRepository.existsById(1)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(1));
    }
}
