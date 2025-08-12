package com.hexaware.careercrafter.controller;

import com.hexaware.careercrafter.dto.EmployerDTO;
import com.hexaware.careercrafter.service.IEmployerService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employers")
public class EmployerController {

    private static final Logger logger = LoggerFactory.getLogger(EmployerController.class);

    @Autowired
    private IEmployerService employerService;

    @PostMapping
    public ResponseEntity<EmployerDTO> createEmployer(@Valid @RequestBody EmployerDTO dto) {
        logger.info("Request to create employer for userId: {}", dto.getUserId());
        EmployerDTO created = employerService.createEmployer(dto);
        logger.info("Employer created successfully with ID: {}", created.getEmployerId());
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployerDTO> getEmployerById(@PathVariable int id) {
        logger.info("Request to fetch employer with ID: {}", id);
        EmployerDTO employer = employerService.getEmployerById(id);
        logger.info("Successfully fetched employer with ID: {}", id);
        return ResponseEntity.ok(employer);
    }

    @GetMapping
    public ResponseEntity<List<EmployerDTO>> getAllEmployers() {
        logger.info("Request to fetch all employers");
        List<EmployerDTO> employers = employerService.getAllEmployers();
        logger.info("Fetched {} employers", employers.size());
        return ResponseEntity.ok(employers);
    }

    @PutMapping
    public ResponseEntity<EmployerDTO> updateEmployer(@Valid @RequestBody EmployerDTO dto) {
        logger.info("Request to update employer with ID: {}", dto.getEmployerId());
        EmployerDTO updated = employerService.updateEmployer(dto);
        logger.info("Employer with ID {} updated successfully", dto.getEmployerId());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployer(@PathVariable int id) {
        logger.info("Request to delete employer with ID: {}", id);
        employerService.deleteEmployer(id);
        logger.info("Employer with ID {} deleted successfully", id);
        return ResponseEntity.ok("Employer deleted successfully");
    }
}
