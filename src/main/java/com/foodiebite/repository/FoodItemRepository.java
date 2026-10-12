package com.foodiebite.repository;

import com.foodiebite.entity.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, String> {

    List<FoodItem> findByCategoryIgnoreCase(String category);

    List<FoodItem> findByDietaryIgnoreCase(String dietary);

    @Query("SELECT f FROM FoodItem f WHERE " +
           "(:category IS NULL OR :category = 'all' OR LOWER(f.category) = LOWER(:category)) AND " +
           "(:dietary IS NULL OR :dietary = 'all' OR LOWER(f.dietary) = LOWER(:dietary)) AND " +
           "(:search IS NULL OR LOWER(f.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           " LOWER(f.description) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           " LOWER(f.category) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<FoodItem> searchFoods(
            @Param("category") String category,
            @Param("dietary") String dietary,
            @Param("search") String search
    );
}
