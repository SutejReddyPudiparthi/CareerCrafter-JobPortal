package com.hexaware.careercrafter.controller;

import com.hexaware.careercrafter.dto.SearchRecommendationDTO;
import com.hexaware.careercrafter.service.ISearchRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/search-recommendations")
public class SearchRecommendationController {

    @Autowired
    private ISearchRecommendationService searchService;

    @PostMapping
    public SearchRecommendationDTO createSearch(@RequestBody SearchRecommendationDTO searchDTO) {
        return searchService.createSearch(searchDTO);
    }

    @GetMapping("/{id}")
    public SearchRecommendationDTO getSearchById(@PathVariable int id) {
        return searchService.getSearchById(id);
    }

    @GetMapping("/user/{userId}")
    public List<SearchRecommendationDTO> getSearchesByUserId(@PathVariable int userId) {
        return searchService.getSearchesByUserId(userId);
    }

    @PutMapping
    public SearchRecommendationDTO updateSearch(@RequestBody SearchRecommendationDTO searchDTO) {
        return searchService.updateSearch(searchDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteSearch(@PathVariable int id) {
        searchService.deleteSearch(id);
    }
}
