package com.hexaware.careercrafter.controller;

import com.hexaware.careercrafter.dto.UserDTO;
import com.hexaware.careercrafter.service.IUserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        logger.info("Request to create a new user with email: {}", userDTO.getEmail());
        UserDTO createdUser = userService.createUser(userDTO);
        logger.info("User created successfully with ID: {}", createdUser.getUserId());
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable int id) {
        logger.info("Request to fetch user with ID: {}", id);
        UserDTO user = userService.getUserById(id);
        logger.info("Successfully fetched user with ID: {}", id);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        logger.info("Request to fetch all users");
        List<UserDTO> users = userService.getAllUsers();
        logger.info("Successfully fetched {} users", users.size());
        return ResponseEntity.ok(users);
    }

    @PutMapping
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO) {
        logger.info("Request to update user with ID: {}", userDTO.getUserId());
        UserDTO updatedUser = userService.updateUser(userDTO);
        logger.info("User with ID {} updated successfully", userDTO.getUserId());
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        logger.info("Request to delete user with ID: {}", id);
        userService.deleteUser(id);
        logger.info("User with ID {} deleted successfully", id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
