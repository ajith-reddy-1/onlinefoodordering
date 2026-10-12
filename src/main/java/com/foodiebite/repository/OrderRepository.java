package com.foodiebite.repository;

import com.foodiebite.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    List<Order> findAllByOrderByCreatedAtDesc();

    List<Order> findByUserIdOrderByCreatedAtDesc(String userId);

    List<Order> findByCustomerEmailOrderByCreatedAtDesc(String email);

    @Query("SELECT o FROM Order o WHERE " +
           "(:userId IS NULL OR o.userId = :userId OR o.customerEmail = :userId) AND " +
           "(:status IS NULL OR :status = 'all' OR LOWER(o.status) = LOWER(:status)) " +
           "ORDER BY o.createdAt DESC")
    List<Order> filterOrders(@Param("userId") String userId, @Param("status") String status);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.status IN ('Pending', 'Preparing', 'Out for Delivery')")
    long countActiveOrders();

    @Query("SELECT COALESCE(SUM(o.total), 0) FROM Order o WHERE o.status != 'Cancelled'")
    Double calculateTotalRevenue();
}
