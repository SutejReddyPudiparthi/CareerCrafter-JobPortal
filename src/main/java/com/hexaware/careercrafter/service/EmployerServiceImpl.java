package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.dto.EmployerDTO;
import com.hexaware.careercrafter.entities.Employer;
import com.hexaware.careercrafter.entities.User;
import com.hexaware.careercrafter.exception.InvalidRequestException;
import com.hexaware.careercrafter.exception.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.EmployerRepository;
import com.hexaware.careercrafter.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/*
 * Implementation of IEmployerService.
 * Implements employer-related operations.
 */


@Service
public class EmployerServiceImpl implements IEmployerService {

    private static final Logger logger = LoggerFactory.getLogger(EmployerServiceImpl.class);

    @Autowired
    private EmployerRepository employerRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public EmployerDTO createEmployer(EmployerDTO employerDTO) {
        logger.debug("Creating employer for userId: {}, companyName: {}", employerDTO.getUserId(), employerDTO.getCompanyName());

        if (employerDTO.getUserId() == 0 || employerDTO.getCompanyName() == null) {
            logger.error("Invalid employer creation request - missing userId or companyName");
            throw new InvalidRequestException("UserId and Company Name must be provided.");
        }

        User user = userRepository.findById(employerDTO.getUserId())
                .orElseThrow(() -> {
                    logger.error("Invalid UserId: {}", employerDTO.getUserId());
                    return new InvalidRequestException("Invalid UserId: " + employerDTO.getUserId());
                });

        Employer employer = dtoToEntity(employerDTO);
        employer.setUser(user);
        Employer saved = employerRepository.save(employer);
        logger.info("Employer created successfully with ID: {}", saved.getEmployerId());
        return entityToDto(saved);
    }

    @Override
    public List<EmployerDTO> getAllEmployers() {
        logger.debug("Fetching all employers from database");
        List<Employer> employers = employerRepository.findAll();
        List<EmployerDTO> dtoList = new ArrayList<>();
        for (Employer employer : employers) {
            dtoList.add(entityToDto(employer));
        }
        logger.info("Fetched {} employers", dtoList.size());
        return dtoList;
    }

    @Override
    public EmployerDTO getEmployerById(int id) {
        logger.debug("Fetching employer with ID: {}", id);
        Employer employer = employerRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Employer with ID {} not found", id);
                    return new ResourceNotFoundException("Employer not found with ID: " + id);
                });
        return entityToDto(employer);
    }

    @Override
    public void deleteEmployer(int id) {
        logger.debug("Deleting employer with ID: {}", id);
        if (!employerRepository.existsById(id)) {
            logger.error("Cannot delete - employer with ID {} does not exist", id);
            throw new ResourceNotFoundException("Cannot delete. Employer with ID " + id + " does not exist.");
        }
        employerRepository.deleteById(id);
        logger.info("Employer with ID {} deleted successfully", id);
    }

    @Override
    public EmployerDTO updateEmployer(EmployerDTO employerDTO) {
        logger.debug("Updating employer with ID: {}", employerDTO.getEmployerId());
        if (!employerRepository.existsById(employerDTO.getEmployerId())) {
            logger.error("Cannot update - employer with ID {} not found", employerDTO.getEmployerId());
            throw new ResourceNotFoundException("Cannot update. Employer with ID " + employerDTO.getEmployerId() + " not found.");
        }
        User user = userRepository.findById(employerDTO.getUserId())
                .orElseThrow(() -> {
                    logger.error("Invalid UserId: {}", employerDTO.getUserId());
                    return new InvalidRequestException("Invalid UserId: " + employerDTO.getUserId());
                });

        Employer employer = dtoToEntity(employerDTO);
        employer.setUser(user);
        Employer updated = employerRepository.save(employer);
        logger.info("Employer with ID {} updated successfully", updated.getEmployerId());
        return entityToDto(updated);
    }

    private EmployerDTO entityToDto(Employer employer) {
        EmployerDTO dto = new EmployerDTO();
        dto.setEmployerId(employer.getEmployerId());
        dto.setUserId(employer.getUser().getUserId());
        dto.setCompanyName(employer.getCompanyName());
        dto.setCompanyDescription(employer.getCompanyDescription());
        return dto;
    }

    private Employer dtoToEntity(EmployerDTO dto) {
        Employer employer = new Employer();
        employer.setEmployerId(dto.getEmployerId());
        employer.setCompanyName(dto.getCompanyName());
        employer.setCompanyDescription(dto.getCompanyDescription());
        return employer;
    }
}
