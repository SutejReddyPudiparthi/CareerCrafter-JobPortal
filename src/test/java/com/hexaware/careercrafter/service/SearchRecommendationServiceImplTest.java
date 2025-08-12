package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.dto.SearchRecommendationDTO;
import com.hexaware.careercrafter.entities.SearchRecommendation;
import com.hexaware.careercrafter.exception.InvalidRequestException;
import com.hexaware.careercrafter.exception.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.SearchRecommendationRepository;
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
class SearchRecommendationServiceImplTest {

    @Mock SearchRecommendationRepository repo;
    @InjectMocks SearchRecommendationServiceImpl service;

    SearchRecommendationDTO dto;
    SearchRecommendation entity;

    @BeforeEach
    void setup() {
        dto = new SearchRecommendationDTO();
        dto.setSearchId(1);
        dto.setUserId(2);
        dto.setSearchKeywords("Java Developer");

        entity = new SearchRecommendation();
        entity.setSearchId(1);
    }

    @Test
    void create_success() {
        when(repo.save(any())).thenReturn(entity);
        assertNotNull(service.createSearch(dto));
    }

    @Test
    void create_invalid() {
        dto.setSearchKeywords(null);
        assertThrows(InvalidRequestException.class, () -> service.createSearch(dto));
    }

    @Test
    void getById_found() {
        when(repo.findById(1)).thenReturn(Optional.of(entity));
        assertNotNull(service.getSearchById(1));
    }

    @Test
    void update_notFound() {
        when(repo.existsById(1)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> service.updateSearch(dto));
    }

    @Test
    void delete_exists() {
        when(repo.existsById(1)).thenReturn(true);
        service.deleteSearch(1);
        verify(repo).deleteById(1);
    }
}
