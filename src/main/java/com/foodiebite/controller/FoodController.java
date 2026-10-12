package com.foodiebite.controller;

import com.foodiebite.entity.FoodItem;
import com.foodiebite.repository.FoodItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/foods")
@CrossOrigin(origins = "*")
public class FoodController {

    @Autowired
    private FoodItemRepository foodItemRepository;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getFoods(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String dietary,
            @RequestParam(required = false) String search) {

        List<FoodItem> foods = foodItemRepository.searchFoods(category, dietary, search);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("count", foods.size());
        response.put("foods", foods);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getFoodById(@PathVariable String id) {
        Optional<FoodItem> food = foodItemRepository.findById(id);
        Map<String, Object> response = new HashMap<>();

        if (food.isPresent()) {
            response.put("success", true);
            response.put("food", food.get());
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Food item not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createFood(@RequestBody FoodItem foodItem) {
        Map<String, Object> response = new HashMap<>();

        if (foodItem.getName() == null || foodItem.getPrice() == null) {
            response.put("success", false);
            response.put("message", "Name and price are required");
            return ResponseEntity.badRequest().body(response);
        }

        if (foodItem.getId() == null || foodItem.getId().isBlank()) {
            foodItem.setId("f-" + System.currentTimeMillis());
        }

        FoodItem saved = foodItemRepository.save(foodItem);
        response.put("success", true);
        response.put("message", "Food item added successfully");
        response.put("food", saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateFood(
            @PathVariable String id,
            @RequestBody FoodItem foodUpdates) {

        Map<String, Object> response = new HashMap<>();
        Optional<FoodItem> existing = foodItemRepository.findById(id);

        if (existing.isEmpty()) {
            response.put("success", false);
            response.put("message", "Food item not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        FoodItem food = existing.get();
        if (foodUpdates.getName() != null) food.setName(foodUpdates.getName());
        if (foodUpdates.getCategory() != null) food.setCategory(foodUpdates.getCategory());
        if (foodUpdates.getPrice() != null) food.setPrice(foodUpdates.getPrice());
        if (foodUpdates.getRating() != null) food.setRating(foodUpdates.getRating());
        if (foodUpdates.getPrepTime() != null) food.setPrepTime(foodUpdates.getPrepTime());
        if (foodUpdates.getDietary() != null) food.setDietary(foodUpdates.getDietary());
        if (foodUpdates.getBadge() != null) food.setBadge(foodUpdates.getBadge());
        if (foodUpdates.getDescription() != null) food.setDescription(foodUpdates.getDescription());
        if (foodUpdates.getImage() != null) food.setImage(foodUpdates.getImage());
        if (foodUpdates.getAvailable() != null) food.setAvailable(foodUpdates.getAvailable());

        FoodItem saved = foodItemRepository.save(food);
        response.put("success", true);
        response.put("message", "Food item updated successfully");
        response.put("food", saved);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteFood(@PathVariable String id) {
        Map<String, Object> response = new HashMap<>();

        if (!foodItemRepository.existsById(id)) {
            response.put("success", false);
            response.put("message", "Food item not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        foodItemRepository.deleteById(id);
        response.put("success", true);
        response.put("message", "Food item deleted successfully");
        return ResponseEntity.ok(response);
    }
}
