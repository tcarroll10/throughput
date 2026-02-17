package com.tcarroll10.throughPut.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class ApiKeyAuthFilter extends OncePerRequestFilter {

    private static final String VALID_API_KEY = "test-api-key-12345";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        // Skip health check endpoint (for Kubernetes)
        if (request.getRequestURI().equals("/actuator/health")) {
            filterChain.doFilter(request, response);
            return;
        }

        String apiKey = request.getHeader("X-API-Key");

        if (VALID_API_KEY.equals(apiKey)) {
            // Simulate JWT validation overhead
            // Real JWT validation takes 1-5ms
            try {
                Thread.sleep(2); // 2ms validation time
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken("api-client", null,
                            List.of(new SimpleGrantedAuthority("ROLE_API_CLIENT")));
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\":\"Unauthorized - Invalid or missing API key\"}");
        }
    }
}