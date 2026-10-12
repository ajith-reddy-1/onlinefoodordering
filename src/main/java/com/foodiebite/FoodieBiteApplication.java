package com.foodiebite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FoodieBiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodieBiteApplication.class, args);
        System.out.println("""
        ========================================================
        🍕 FOODIEBITE - SPRING BOOT SERVER STARTED 🚀
        ========================================================
          🌐 Storefront URL   : http://localhost:8081
          🔐 Customer Login   : http://localhost:8081/login
          ⚙️  Admin Dashboard  : http://localhost:8081/admin
          🗄️  Database        : MySQL (foodiedb on port 3306)
        ========================================================
          Demo Accounts:
            • Customer : customer@foodie.com / user123
            • Admin    : admin@foodie.com / admin123
        ========================================================
        """);
    }
}
