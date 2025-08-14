package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.dto.UserDTO;
import com.hexaware.careercrafter.entities.User;
import com.hexaware.careercrafter.exception.*;
import com.hexaware.careercrafter.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/*
 * Implementation of IUserService.
 * Implements user-related operations.
 */


@Service
public class UserServiceImpl implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        logger.debug("Attempting to create a user with email: {}", userDTO.getEmail());

        if (userDTO.getEmail() == null || userDTO.getPassword() == null || userDTO.getName() == null) {
            logger.error("Failed to create user - missing required fields");
            throw new InvalidRequestException("Name, Email, and Password are required.");
        }

        if (userRepository.findByEmail(userDTO.getEmail()) != null) {
            logger.error("Failed to create user - email {} already exists", userDTO.getEmail());
            throw new DuplicateResourceException("Email already exists.");
        }

        User savedUser = userRepository.save(convertToEntity(userDTO));
        logger.info("User created successfully with ID: {}", savedUser.getUserId());
        return convertToDTO(savedUser);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        logger.debug("Fetching all users from database");
        return userRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(int userId) {
        logger.debug("Fetching user with ID: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.error("User with ID {} not found", userId);
                    return new ResourceNotFoundException("User with ID " + userId + " not found.");
                });
        return convertToDTO(user);
    }

    @Override
    public void deleteUser(int userId) {
        logger.debug("Deleting user with ID: {}", userId);
        if (!userRepository.existsById(userId)) {
            logger.error("Cannot delete - User with ID {} not found", userId);
            throw new ResourceNotFoundException("User with ID " + userId + " not found.");
        }
        userRepository.deleteById(userId);
        logger.info("User with ID {} deleted successfully", userId);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        logger.debug("Updating user with ID: {}", userDTO.getUserId());

        if (userDTO.getUserId() <= 0 || userDTO.getName() == null || userDTO.getEmail() == null) {
            logger.error("Invalid request for updating user - ID, Name, and Email are required");
            throw new InvalidRequestException("User ID, Name, and Email are required for update.");
        }

        userRepository.findById(userDTO.getUserId())
                .orElseThrow(() -> {
                    logger.error("Cannot update - User with ID {} not found", userDTO.getUserId());
                    return new ResourceNotFoundException("User with ID " + userDTO.getUserId() + " not found.");
                });

        User existingByEmail = userRepository.findByEmail(userDTO.getEmail());
        if (existingByEmail != null && existingByEmail.getUserId() != userDTO.getUserId()) {
            logger.error("Cannot update - Email {} is already used by another user", userDTO.getEmail());
            throw new DuplicateResourceException("Email already in use by another user.");
        }

        User updatedUser = userRepository.save(convertToEntity(userDTO));
        logger.info("User with ID {} updated successfully", updatedUser.getUserId());
        return convertToDTO(updatedUser);
    }

    private User convertToEntity(UserDTO dto) {
        User user = new User();
        user.setUserId(dto.getUserId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setUserType(dto.getUserType());
        return user;
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setUserType(user.getUserType());
        return dto;
    }
}
