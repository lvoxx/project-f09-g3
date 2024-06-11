package com.cp2396f09_sem4_grp3.onmart_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cp2396f09_sem4_grp3.onmart_common.entities.enumeration.ERole;
import com.cp2396f09_sem4_grp3.onmart_service.helper.components.RestAuthenticationEntryPoint;
import com.cp2396f09_sem4_grp3.onmart_service.security.JwtAuthFilter;
import com.cp2396f09_sem4_grp3.onmart_service.service.UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final PasswordEncoder passwordEncoder;
        private final JwtAuthFilter jwtAuthFilter;
        private final UserService userService;
        private RestAuthenticationEntryPoint authenticationEntryPoint;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                return http
                                // Disable CSRF
                                .csrf(AbstractHttpConfigurer::disable)
                                .authorizeHttpRequests(
                                                auth -> auth
                                                                // Public access
                                                                .requestMatchers(
                                                                                // APIs Doc
                                                                                "/swagger-ui", "/swagger-ui/**",
                                                                                "/error", "/v3/api-docs/**",
                                                                                // Grant Access
                                                                                "/register", "/register/**",
                                                                                "/login", "/login/**",
                                                                                // Public APIs
                                                                                "/public/**",
                                                                                // Dashboard
                                                                                "/dashboard/**",
                                                                                // VNPay Callback
                                                                                "/payment/vn-pay-callback/**")
                                                                .permitAll()
                                                                // Generic access for auth user
                                                                .requestMatchers(
                                                                                "/account", "/account/**",
                                                                                "/private/**",
                                                                                "/payment/vn-pay")
                                                                .hasAnyAuthority(
                                                                                ERole.CUSTOMER.name(),
                                                                                ERole.SELLER.name(),
                                                                                ERole.WAREHOUSE_STAFFS.name(),
                                                                                ERole.ADMIN.name())
                                                                // Disallow everything else..
                                                                .anyRequest().authenticated())
                                // No session will be created or used by spring security
                                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authenticationProvider(authenticationProvider())
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                                .exceptionHandling(ex -> ex.authenticationEntryPoint(authenticationEntryPoint))
                                .build();
        }

        @Bean
        public AuthenticationProvider authenticationProvider() {
                DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
                authenticationProvider.setUserDetailsService(userService.userDetailsFromUser());
                authenticationProvider.setPasswordEncoder(passwordEncoder);
                return authenticationProvider;
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
                return config.getAuthenticationManager();
        }

}
