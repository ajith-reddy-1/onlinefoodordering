package com.foodiebite;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbCheck {
    public static void main(String[] args) {
        String[] passwords = {
            "root", "admin", "1234", "123456", "12345678", "root123", "root@123",
            "admin123", "password", "mysql", "sindhu", "sindhu123", "sindhu@123",
            "Root@123", "Admin@123", "MySQL@123", "P@ssword", "P@ssword1"
        };

        for (String p : passwords) {
            try {
                String url = "jdbc:mysql://localhost:3306/mysql?useSSL=false&allowPublicKeyRetrieval=true";
                try (Connection conn = DriverManager.getConnection(url, "root", p)) {
                    System.out.println("FOUND_MYSQL_PASSWORD: [" + p + "]");
                    return;
                }
            } catch (Exception ignored) {
            }
        }
        System.out.println("NO_PASSWORD_MATCHED_IN_LIST");
    }
}
