package com.foodiebite.controller;

import com.foodiebite.entity.Category;
import com.foodiebite.entity.FoodItem;
import com.foodiebite.entity.Order;
import com.foodiebite.repository.CategoryRepository;
import com.foodiebite.repository.FoodItemRepository;
import com.foodiebite.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class WebController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FoodItemRepository foodItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    /**
     * Home & Storefront Page
     */
    @GetMapping({"/", "/index", "/index.html"})
    public String index(Model model) {
        List<Category> categories = categoryRepository.findAll();
        List<FoodItem> foods = foodItemRepository.findAll();

        model.addAttribute("categories", categories);
        model.addAttribute("foods", foods);
        return "index";
    }

    /**
     * Customer & Admin Login Page
     */
    @GetMapping({"/login", "/login.html"})
    public String login() {
        return "login";
    }

    /**
     * Admin Dashboard Operations Page
     */
    @GetMapping({"/admin", "/admin.html"})
    public String admin(Model model) {
        List<Order> orders = orderRepository.findAllByOrderByCreatedAtDesc();
        List<FoodItem> foods = foodItemRepository.findAll();
        List<Category> categories = categoryRepository.findAll();

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

        model.addAttribute("stats", stats);
        model.addAttribute("orders", orders);
        model.addAttribute("foods", foods);
        model.addAttribute("categories", categories);

        return "admin";
    }
}
