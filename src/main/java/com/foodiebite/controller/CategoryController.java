package com.foodiebite.controller;

import com.foodiebite.entity.Category;
import com.foodiebite.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("categories", categories);
        return ResponseEntity.ok(response);
    }
}
