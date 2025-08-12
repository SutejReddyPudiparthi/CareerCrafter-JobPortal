package com.hexaware.careercrafter.controller;

import com.hexaware.careercrafter.dto.EmployerDTO;
import com.hexaware.careercrafter.service.IEmployerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employers")
public class EmployerController {

    @Autowired
    private IEmployerService employerService;

    @PostMapping
    public ResponseEntity<EmployerDTO> createEmployer(@Valid @RequestBody EmployerDTO dto) {
        return new ResponseEntity<>(employerService.createEmployer(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployerDTO> getEmployerById(@PathVariable int id) {
        return ResponseEntity.ok(employerService.getEmployerById(id));
    }

    @GetMapping
    public ResponseEntity<List<EmployerDTO>> getAllEmployers() {
        return ResponseEntity.ok(employerService.getAllEmployers());
    }

    @PutMapping
    public ResponseEntity<EmployerDTO> updateEmployer(@Valid @RequestBody EmployerDTO dto) {
        return ResponseEntity.ok(employerService.updateEmployer(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployer(@PathVariable int id) {
        employerService.deleteEmployer(id);
        return ResponseEntity.ok("Employer deleted successfully");
    }
}
