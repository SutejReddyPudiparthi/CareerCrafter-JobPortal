package com.hexaware.careercrafter.controller;

import com.hexaware.careercrafter.dto.UserDTO;
import com.hexaware.careercrafter.repository.UserRepository;
import com.hexaware.careercrafter.security.JwtUtil;
import com.hexaware.careercrafter.security.CustomUserDetailsService;

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/*
 * Rest Controller for authentication operations.
 * Handles user registration and login.
 */

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Authentication and registration APIs")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email is already registered!");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
    }

    @Operation(summary = "Login and retrieve a JWT token")
    @PostMapping("/login")
    public ResponseEntity<?> createToken(@RequestBody Map<String, String> loginData) throws Exception {
        String email = loginData.get("email");
        String password = loginData.get("password");
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        final String token = jwtUtil.generateToken(userDetails);
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }
    
}
