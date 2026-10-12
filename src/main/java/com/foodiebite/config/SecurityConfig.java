package com.foodiebite.config;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Disable CSRF for REST APIs and AJAX fetch operations
            .csrf(csrf -> csrf.disable())

            // 2. EXPLICITLY DISABLE HTTP Basic Authentication
            // Ensures the server never issues WWW-Authenticate: Basic challenge headers
            .httpBasic(httpBasic -> httpBasic.disable())

            // 3. Configure URL & Dispatcher Authorization Rules
            .authorizeHttpRequests(auth -> auth
                // Allow internal view dispatching and error handling
                .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()

                // Allow static resources (CSS, JS, images, icons)
                .requestMatchers(
                    "/css/**",
                    "/js/**",
                    "/images/**",
                    "/favicon.ico",
                    "/error"
                ).permitAll()

                // Allow public storefront and login/register pages
                .requestMatchers(
                    "/",
                    "/index",
                    "/index.html",
                    "/login",
                    "/login.html",
                    "/perform_login"
                ).permitAll()

                // Allow public REST APIs for storefront browsing and ordering
                .requestMatchers(
                    "/api/foods/**",
                    "/api/categories/**",
                    "/api/auth/**",
                    "/api/coupon/**",
                    "/api/orders",
                    "/api/orders/**"
                ).permitAll()

                // Protect Admin Dashboard and Admin APIs - requires ADMIN role
                .requestMatchers(
                    "/admin",
                    "/admin.html",
                    "/api/admin/**"
                ).hasRole("ADMIN")

                // Any other request requires authentication
                .anyRequest().authenticated()
            )

            // 4. Form Login configuration pointing to custom /login page
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/perform_login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/", false)
                .failureUrl("/login?error=true")
                .permitAll()
            )

            // 5. Logout configuration
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )

            // 6. Exception handling: redirect unauthenticated access to /login
            .exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
                .accessDeniedPage("/login?denied=true")
            );

        return http.build();
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                if (rawPassword == null || encodedPassword == null) return false;
                return rawPassword.toString().equals(encodedPassword);
            }
        };
    }
}
