package com.huy.springsecurity.config;

import com.huy.springsecurity.authentication.authprovider.OtpAuthenticationProvider;
import com.huy.springsecurity.authentication.authprovider.UsernamePasswordAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AuthenticationManagerConfig {

    private final UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;
    private final OtpAuthenticationProvider otpAuthenticationProvider;


    @Bean
    public AuthenticationManager authManagerBean() {
        return new ProviderManager(List.of(
                usernamePasswordAuthenticationProvider,
                otpAuthenticationProvider
        ));
    }
}
