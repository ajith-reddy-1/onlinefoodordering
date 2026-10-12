package com.foodiebite.controller;

import com.foodiebite.config.CustomUserDetailsService;
import com.foodiebite.dto.LoginRequest;
import com.foodiebite.dto.RegisterRequest;
import com.foodiebite.entity.User;
import com.foodiebite.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private SecurityContextRepository securityContextRepository;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(
            @RequestBody LoginRequest request,
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse) {

        Map<String, Object> response = new HashMap<>();

        if (request.getEmail() == null || request.getPassword() == null) {
            response.put("success", false);
            response.put("message", "Email and password are required");
            return ResponseEntity.badRequest().body(response);
        }

        Optional<User> userOpt = userRepository.findByEmailIgnoreCase(request.getEmail().trim());

        if (userOpt.isEmpty() || !userOpt.get().getPassword().equals(request.getPassword())) {
            response.put("success", false);
            response.put("message", "Invalid email or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        User user = userOpt.get();

        // Establish Spring Security Authentication Session
        try {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    user.getPassword(),
                    userDetails.getAuthorities()
            );
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authToken);
            SecurityContextHolder.setContext(context);
            securityContextRepository.saveContext(context, httpRequest, httpResponse);
        } catch (Exception e) {
            // Log silently and proceed
        }

        // Mask password in JSON response
        Map<String, Object> safeUser = new HashMap<>();
        safeUser.put("id", user.getId());
        safeUser.put("name", user.getName());
        safeUser.put("email", user.getEmail());
        safeUser.put("role", user.getRole());
        safeUser.put("phone", user.getPhone());
        safeUser.put("address", user.getAddress());

        response.put("success", true);
        response.put("message", "Login successful");
        response.put("user", safeUser);
        response.put("token", "token-" + user.getId() + "-" + System.currentTimeMillis());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody RegisterRequest request) {
        Map<String, Object> response = new HashMap<>();

        if (request.getEmail() == null || request.getPassword() == null || request.getName() == null) {
            response.put("success", false);
            response.put("message", "Name, email, and password are required");
            return ResponseEntity.badRequest().body(response);
        }

        if (userRepository.existsByEmailIgnoreCase(request.getEmail().trim())) {
            response.put("success", false);
            response.put("message", "An account with this email already exists");
            return ResponseEntity.badRequest().body(response);
        }

        User user = new User(
                "u-" + System.currentTimeMillis(),
                request.getName().trim(),
                request.getEmail().trim(),
                request.getPassword(),
                "customer",
                request.getPhone(),
                request.getAddress()
        );

        User saved = userRepository.save(user);

        Map<String, Object> safeUser = new HashMap<>();
        safeUser.put("id", saved.getId());
        safeUser.put("name", saved.getName());
        safeUser.put("email", saved.getEmail());
        safeUser.put("role", saved.getRole());
        safeUser.put("phone", saved.getPhone());
        safeUser.put("address", saved.getAddress());

        response.put("success", true);
        response.put("message", "Registration successful");
        response.put("user", safeUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getCurrentUser(@RequestParam String userId) {
        Map<String, Object> response = new HashMap<>();
        Optional<User> userOpt = userRepository.findById(userId);

        if (userOpt.isEmpty()) {
            response.put("success", false);
            response.put("message", "User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        User user = userOpt.get();
        Map<String, Object> safeUser = new HashMap<>();
        safeUser.put("id", user.getId());
        safeUser.put("name", user.getName());
        safeUser.put("email", user.getEmail());
        safeUser.put("role", user.getRole());
        safeUser.put("phone", user.getPhone());
        safeUser.put("address", user.getAddress());

        response.put("success", true);
        response.put("user", safeUser);
        return ResponseEntity.ok(response);
    }
}
