package com.foodiebite.controller;

import com.foodiebite.repository.FoodItemRepository;
import com.foodiebite.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private FoodItemRepository foodItemRepository;

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        Double revenue = orderRepository.calculateTotalRevenue();
        if (revenue == null) revenue = 0.0;
        revenue = BigDecimal.valueOf(revenue).setScale(2, RoundingMode.HALF_UP).doubleValue();

        long totalOrders = orderRepository.count();
        long activeOrders = orderRepository.countActiveOrders();
        long totalFoods = foodItemRepository.count();

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalRevenue", revenue);
        stats.put("totalOrders", totalOrders);
        stats.put("activeOrders", activeOrders);
        stats.put("totalFoods", totalFoods);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("stats", stats);

        return ResponseEntity.ok(response);
    }
}
