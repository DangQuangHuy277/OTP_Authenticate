package com.huy.springsecurity.authentication.filter;

import com.huy.springsecurity.authentication.auth.OtpAuthentication;
import com.huy.springsecurity.authentication.auth.UsernamePasswordAuthentication;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class InitialAuthenticationFilter extends OncePerRequestFilter {
    Logger logger = Logger.getLogger(InitialAuthenticationFilter.class.getName());

    private final AuthenticationManager manager;
    @Value("${jwt.signing.key}")
    private String signingKey;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String username = request.getHeader("username");
        String password = request.getHeader("password");
        String code = request.getHeader("code");

        logger.info("Do filter from initial filter");

        if (code == null) {
            Authentication a = new UsernamePasswordAuthentication(username, password);
            manager.authenticate(a);
        } else {
            logger.info("A request with otp header");
            Authentication a = new OtpAuthentication(username, code);
            a = manager.authenticate(a);

            SecretKey key = Keys.hmacShaKeyFor(
                    signingKey.getBytes(StandardCharsets.UTF_8)
            );

            String jwt = Jwts.builder()
                    .setClaims(Map.of("username", username))
                    .signWith(key)
                    .compact();
            response.setHeader("Authorization", jwt);
        }

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/login");

    }
}
