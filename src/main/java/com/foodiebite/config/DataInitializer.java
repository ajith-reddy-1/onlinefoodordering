package com.foodiebite.config;

import com.foodiebite.entity.*;
import com.foodiebite.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FoodItemRepository foodItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void run(String... args) throws Exception {
        seedCategories();
        seedFoodItems();
        seedUsers();
        seedSampleOrders();
    }

    private void seedCategories() {
        if (categoryRepository.count() == 0) {
            categoryRepository.saveAll(List.of(
                new Category("all", "All Items", "fa-utensils"),
                new Category("pizza", "Pizza", "fa-pizza-slice"),
                new Category("burgers", "Burgers", "fa-hamburger"),
                new Category("pasta", "Pasta", "fa-bowl-food"),
                new Category("asian", "Asian Dishes", "fa-bowl-rice"),
                new Category("desserts", "Desserts", "fa-cake-candles"),
                new Category("beverages", "Beverages", "fa-mug-hot")
            ));
            System.out.println("[Database Seeder] Initialized categories in MySQL.");
        }
    }

    private void seedFoodItems() {
        if (foodItemRepository.count() == 0) {
            foodItemRepository.saveAll(List.of(
                new FoodItem("f-1", "Margherita Gourmet Pizza", "pizza", 12.99, 4.8, "20-25 mins", "veg", "Best Seller",
                    "Stone-baked artisan pizza topped with fresh San Marzano tomato sauce, buffalo mozzarella, and fragrant garden basil leaves.",
                    "https://images.unsplash.com/photo-1604382355076-af4b0eb60143?w=800&auto=format&fit=crop&q=80", true),
                new FoodItem("f-2", "Pepperoni Supreme Pizza", "pizza", 15.49, 4.9, "20-25 mins", "non-veg", "Popular",
                    "Loaded with double layers of crispy Italian pepperoni, melted mozzarella cheese, oregano, and zesty marinara sauce.",
                    "https://images.unsplash.com/photo-1628840042765-356cda07504e?w=800&auto=format&fit=crop&q=80", true),
                new FoodItem("f-3", "Smoked BBQ Bacon Burger", "burgers", 11.99, 4.7, "15-20 mins", "non-veg", "Chef Special",
                    "Juicy flame-grilled beef patty, crispy bacon strips, smoked barbecue sauce, caramelized onions, and aged cheddar on a toasted brioche bun.",
                    "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=800&auto=format&fit=crop&q=80", true),
                new FoodItem("f-4", "Crispy Paneer & Veggie Burger", "burgers", 9.49, 4.6, "15-20 mins", "veg", "Vegetarian",
                    "Golden spiced paneer cutlet layered with crunchy iceberg lettuce, ripe tomatoes, spicy garlic mayo, and pickles.",
                    "https://images.unsplash.com/photo-1550547660-d9450f859349?w=800&auto=format&fit=crop&q=80", true),
                new FoodItem("f-5", "Creamy Truffle Alfredo Fettuccine", "pasta", 13.99, 4.8, "18-22 mins", "veg", "Trending",
                    "Silky fettuccine ribbons tossed in a rich parmesan and truffle garlic cream sauce, garnished with fresh parsley and crushed black pepper.",
                    "https://images.unsplash.com/photo-1645112411341-6c4fd023714a?w=800&auto=format&fit=crop&q=80", true),
                new FoodItem("f-6", "Spicy Grilled Chicken Arrabiata", "pasta", 14.79, 4.7, "20-25 mins", "non-veg", "Spicy",
                    "Al dente penne pasta in a fiery garlic chili tomato reduction with tender marinated grilled chicken breast chunks.",
                    "https://images.unsplash.com/photo-1621996346565-e3d5d6281691?w=800&auto=format&fit=crop&q=80", true),
                new FoodItem("f-7", "Japanese Crispy Chicken Katsu Curry", "asian", 16.50, 4.9, "22-26 mins", "non-veg", "Chef Special",
                    "Panko-crusted chicken fillet fried to golden perfection, served with sweet-savory aromatic Japanese curry sauce and steamed sushi rice.",
                    "https://images.unsplash.com/photo-1604908176997-125f25cc6f3d?w=800&auto=format&fit=crop&q=80", true),
                new FoodItem("f-8", "Pan-Seared Veggie Gyoza Dumplings", "asian", 8.99, 4.6, "12-15 mins", "veg", "Vegetarian",
                    "Handmade Japanese dumplings stuffed with minced cabbage, shiitake mushrooms, and scallions, served with tangy ginger soy dip.",
                    "https://images.unsplash.com/photo-1541696432-82c6da8ce7bf?w=800&auto=format&fit=crop&q=80", true),
                new FoodItem("f-9", "Warm Belgian Chocolate Lava Cake", "desserts", 6.99, 4.9, "10-12 mins", "veg", "Sweet Tooth",
                    "Decadent dark chocolate sponge cake with a warm, gooey molten fudge center, served with powdered sugar.",
                    "https://images.unsplash.com/photo-1606313564200-e75d5e30476c?w=800&auto=format&fit=crop&q=80", true),
                new FoodItem("f-10", "Classic New York Strawberry Cheesecake", "desserts", 7.49, 4.8, "8-10 mins", "veg", "Popular",
                    "Velvety smooth baked cream cheese on a crunchy graham cracker crust, topped with fresh strawberry glaze.",
                    "https://images.unsplash.com/photo-1533134242443-d4fd215305ad?w=800&auto=format&fit=crop&q=80", true),
                new FoodItem("f-11", "Alphonso Mango & Mint Sparkler", "beverages", 4.49, 4.8, "5-8 mins", "veg", "Refreshing",
                    "Fresh seasonal mango pulp blended with crushed mint leaves, lime juice, and chilled sparkling soda.",
                    "https://images.unsplash.com/photo-1513558161293-cdaf765ed2fd?w=800&auto=format&fit=crop&q=80", true),
                new FoodItem("f-12", "Iced Vanilla Caramel Macchiato", "beverages", 4.99, 4.7, "5-8 mins", "veg", "Energy Boost",
                    "Double shot of rich espresso layered with cold vanilla-infused milk, ice cubes, and a buttery caramel drizzle.",
                    "https://images.unsplash.com/photo-1517701550927-30cf4ba1dba5?w=800&auto=format&fit=crop&q=80", true)
            ));
            System.out.println("[Database Seeder] Initialized food menu items in MySQL.");
        }
    }

    private void seedUsers() {
        if (!userRepository.existsByEmailIgnoreCase("admin@foodie.com")) {
            userRepository.save(new User(
                "u-admin", "Admin Manager", "admin@foodie.com", "admin123", "admin", "+1 (555) 019-2834", "100 Restaurant Ave, Springfield"
            ));
        }

        if (!userRepository.existsByEmailIgnoreCase("customer@foodie.com")) {
            userRepository.save(new User(
                "u-demo", "Alex Johnson", "customer@foodie.com", "user123", "customer", "+1 (555) 345-6789", "742 Evergreen Terrace, Springfield"
            ));
        }
    }

    private void seedSampleOrders() {
        if (orderRepository.count() == 0) {
            Order o1 = new Order();
            o1.setId("ORD-1001");
            o1.setUserId("u-demo");
            o1.setCustomerName("Alex Johnson");
            o1.setCustomerEmail("customer@foodie.com");
            o1.setCustomerPhone("+1 (555) 345-6789");
            o1.setDeliveryAddress("742 Evergreen Terrace, Springfield, IL 62704");
            o1.setSubtotal(30.47);
            o1.setTax(1.52);
            o1.setDeliveryFee(0.00);
            o1.setDiscount(5.00);
            o1.setTotal(26.99);
            o1.setPaymentMethod("Credit / Debit Card");
            o1.setPaymentStatus("Paid");
            o1.setStatus("Delivered");
            o1.setNotes("Please leave at front door.");
            o1.setCreatedAt(LocalDateTime.now().minusHours(2));

            o1.addItem(new OrderItem("f-1", "Margherita Gourmet Pizza", 12.99, 2, "https://images.unsplash.com/photo-1604382355076-af4b0eb60143?w=800&auto=format&fit=crop&q=80"));
            o1.addItem(new OrderItem("f-11", "Alphonso Mango & Mint Sparkler", 4.49, 1, "https://images.unsplash.com/photo-1513558161293-cdaf765ed2fd?w=800&auto=format&fit=crop&q=80"));
            orderRepository.save(o1);

            Order o2 = new Order();
            o2.setId("ORD-1002");
            o2.setUserId("u-demo");
            o2.setCustomerName("Alex Johnson");
            o2.setCustomerEmail("customer@foodie.com");
            o2.setCustomerPhone("+1 (555) 345-6789");
            o2.setDeliveryAddress("742 Evergreen Terrace, Springfield, IL 62704");
            o2.setSubtotal(18.98);
            o2.setTax(0.95);
            o2.setDeliveryFee(2.99);
            o2.setDiscount(0.00);
            o2.setTotal(22.92);
            o2.setPaymentMethod("Cash on Delivery");
            o2.setPaymentStatus("Pending");
            o2.setStatus("Preparing");
            o2.setNotes("Extra ketchup packets please.");
            o2.setCreatedAt(LocalDateTime.now().minusMinutes(15));

            o2.addItem(new OrderItem("f-3", "Smoked BBQ Bacon Burger", 11.99, 1, "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=800&auto=format&fit=crop&q=80"));
            o2.addItem(new OrderItem("f-9", "Warm Belgian Chocolate Lava Cake", 6.99, 1, "https://images.unsplash.com/photo-1606313564200-e75d5e30476c?w=800&auto=format&fit=crop&q=80"));
            orderRepository.save(o2);

            System.out.println("[Database Seeder] Initialized sample orders in MySQL.");
        }
    }
}
