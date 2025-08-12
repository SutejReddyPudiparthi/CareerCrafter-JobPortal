package com.hexaware.careercrafter.controller;

import com.hexaware.careercrafter.dto.SearchRecommendationDTO;
import com.hexaware.careercrafter.service.ISearchRecommendationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/search-recommendations")
public class SearchRecommendationController {

    private static final Logger logger = LoggerFactory.getLogger(SearchRecommendationController.class);

    @Autowired
    private ISearchRecommendationService searchService;

    @PostMapping
    public SearchRecommendationDTO createSearch(@RequestBody SearchRecommendationDTO searchDTO) {
        logger.info("Request to create search recommendation for userId: {}", searchDTO.getUserId());
        SearchRecommendationDTO created = searchService.createSearch(searchDTO);
        logger.info("Search recommendation created successfully with ID: {}", created.getSearchId());
        return created;
    }

    @GetMapping("/{id}")
    public SearchRecommendationDTO getSearchById(@PathVariable int id) {
        logger.info("Request to fetch search recommendation with ID: {}", id);
        SearchRecommendationDTO dto = searchService.getSearchById(id);
        logger.info("Successfully fetched search recommendation with ID: {}", id);
        return dto;
    }

    @GetMapping("/user/{userId}")
    public List<SearchRecommendationDTO> getSearchesByUserId(@PathVariable int userId) {
        logger.info("Request to fetch search recommendations for userId: {}", userId);
        List<SearchRecommendationDTO> list = searchService.getSearchesByUserId(userId);
        logger.info("Fetched {} search recommendations for userId: {}", list.size(), userId);
        return list;
    }

    @PutMapping
    public SearchRecommendationDTO updateSearch(@RequestBody SearchRecommendationDTO searchDTO) {
        logger.info("Request to update search recommendation with ID: {}", searchDTO.getSearchId());
        SearchRecommendationDTO updated = searchService.updateSearch(searchDTO);
        logger.info("Search recommendation with ID {} updated successfully", searchDTO.getSearchId());
        return updated;
    }

    @DeleteMapping("/{id}")
    public void deleteSearch(@PathVariable int id) {
        logger.info("Request to delete search recommendation with ID: {}", id);
        searchService.deleteSearch(id);
        logger.info("Search recommendation with ID {} deleted successfully", id);
    }
}
