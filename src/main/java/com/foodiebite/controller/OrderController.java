package com.foodiebite.controller;

import com.foodiebite.dto.OrderRequest;
import com.foodiebite.entity.Order;
import com.foodiebite.entity.OrderItem;
import com.foodiebite.repository.FoodItemRepository;
import com.foodiebite.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private FoodItemRepository foodItemRepository;

    private static final Map<String, Map<String, Object>> COUPONS = new HashMap<>();

    static {
        Map<String, Object> c1 = new HashMap<>();
        c1.put("code", "FOODIE20");
        c1.put("discountPercent", 20);
        c1.put("description", "20% Off entire order");
        COUPONS.put("FOODIE20", c1);

        Map<String, Object> c2 = new HashMap<>();
        c2.put("code", "WELCOME10");
        c2.put("discountFixed", 5.00);
        c2.put("description", "$5.00 Welcome Discount");
        COUPONS.put("WELCOME10", c2);
    }

    @GetMapping("/coupon/validate")
    public ResponseEntity<Map<String, Object>> validateCoupon(@RequestParam(required = false) String code) {
        Map<String, Object> response = new HashMap<>();

        if (code == null || code.isBlank() || !COUPONS.containsKey(code.trim().toUpperCase())) {
            response.put("success", false);
            response.put("message", "Invalid or expired coupon code");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        response.put("success", true);
        response.put("coupon", COUPONS.get(code.trim().toUpperCase()));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/orders")
    public ResponseEntity<Map<String, Object>> placeOrder(@RequestBody OrderRequest req) {
        Map<String, Object> response = new HashMap<>();

        if (req.getItems() == null || req.getItems().isEmpty()) {
            response.put("success", false);
            response.put("message", "Order must contain at least one item");
            return ResponseEntity.badRequest().body(response);
        }

        Order order = new Order();
        String orderId = "ORD-" + (1000 + new Random().nextInt(9000));
        order.setId(orderId);
        order.setUserId(req.getUserId() != null ? req.getUserId() : "guest");
        order.setCustomerName(req.getCustomerName() != null ? req.getCustomerName() : "Valued Customer");
        order.setCustomerEmail(req.getCustomerEmail());
        order.setCustomerPhone(req.getCustomerPhone() != null ? req.getCustomerPhone() : "");
        order.setDeliveryAddress(req.getDeliveryAddress() != null ? req.getDeliveryAddress() : "");
        order.setNotes(req.getNotes());
        order.setPaymentMethod(req.getPaymentMethod() != null ? req.getPaymentMethod() : "Cash on Delivery");
        order.setPaymentStatus("Cash on Delivery".equalsIgnoreCase(order.getPaymentMethod()) ? "Pending" : "Paid");
        order.setStatus("Pending");
        order.setCreatedAt(LocalDateTime.now());

        double subtotal = 0.0;
        for (OrderRequest.OrderItemDto itemDto : req.getItems()) {
            double price = itemDto.getPrice() != null ? itemDto.getPrice() : 0.0;
            int quantity = itemDto.getQuantity() != null && itemDto.getQuantity() > 0 ? itemDto.getQuantity() : 1;

            OrderItem item = new OrderItem(
                    itemDto.getId(),
                    itemDto.getName(),
                    price,
                    quantity,
                    itemDto.getImage()
            );
            order.addItem(item);
            subtotal += (price * quantity);
        }

        // Apply discount
        double discount = 0.0;
        if (req.getCouponCode() != null && COUPONS.containsKey(req.getCouponCode().trim().toUpperCase())) {
            Map<String, Object> coupon = COUPONS.get(req.getCouponCode().trim().toUpperCase());
            order.setCouponCode(req.getCouponCode().trim().toUpperCase());
            if (coupon.containsKey("discountPercent")) {
                discount = (subtotal * ((Number) coupon.get("discountPercent")).doubleValue()) / 100.0;
            } else if (coupon.containsKey("discountFixed")) {
                discount = Math.min(((Number) coupon.get("discountFixed")).doubleValue(), subtotal);
            }
        }

        double discountedSubtotal = Math.max(0.0, subtotal - discount);
        double tax = round(discountedSubtotal * 0.05);
        double deliveryFee = discountedSubtotal >= 30.00 ? 0.00 : 2.99;
        double total = round(discountedSubtotal + tax + deliveryFee);

        order.setSubtotal(round(subtotal));
        order.setDiscount(round(discount));
        order.setTax(tax);
        order.setDeliveryFee(deliveryFee);
        order.setTotal(total);

        Order saved = orderRepository.save(order);

        response.put("success", true);
        response.put("message", "Order placed successfully!");
        response.put("order", saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/orders")
    public ResponseEntity<Map<String, Object>> getOrders(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String status) {

        List<Order> orders = orderRepository.filterOrders(userId, status);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("count", orders.size());
        response.put("orders", orders);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Map<String, Object>> getOrderById(@PathVariable String id) {
        Optional<Order> order = orderRepository.findById(id);
        Map<String, Object> response = new HashMap<>();

        if (order.isPresent()) {
            response.put("success", true);
            response.put("order", order.get());
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Order not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @RequestMapping(value = "/orders/{id}/status", method = {RequestMethod.PATCH, RequestMethod.POST})
    public ResponseEntity<Map<String, Object>> updateOrderStatus(
            @PathVariable String id,
            @RequestBody Map<String, String> body) {

        Map<String, Object> response = new HashMap<>();
        Optional<Order> orderOpt = orderRepository.findById(id);

        if (orderOpt.isEmpty()) {
            response.put("success", false);
            response.put("message", "Order not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        String newStatus = body.get("status");
        if (newStatus == null || newStatus.isBlank()) {
            response.put("success", false);
            response.put("message", "Status is required");
            return ResponseEntity.badRequest().body(response);
        }

        Order order = orderOpt.get();
        order.setStatus(newStatus);
        if ("Delivered".equalsIgnoreCase(newStatus) && "Cash on Delivery".equalsIgnoreCase(order.getPaymentMethod())) {
            order.setPaymentStatus("Paid");
        }

        Order saved = orderRepository.save(order);

        response.put("success", true);
        response.put("message", "Order status updated");
        response.put("order", saved);
        return ResponseEntity.ok(response);
    }

    private double round(double val) {
        return BigDecimal.valueOf(val).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
