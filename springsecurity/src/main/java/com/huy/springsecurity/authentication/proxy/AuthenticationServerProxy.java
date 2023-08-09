package com.huy.springsecurity.authentication.proxy;

import com.huy.springsecurity.authentication.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class AuthenticationServerProxy {
    private final RestTemplate rest;

    private Logger logger = Logger.getLogger(AuthenticationServerProxy.class.getName());

    @Value("${auth.server.base.url}")
    private String baseUrl;

    public void sendAuth(String username, String password) {
        String url = baseUrl + "/user/auth";

        User body = new User();
        body.setUsername(username);
        body.setPassword(password);
        var request = new HttpEntity<>(body);

        rest.postForEntity(url, request, Void.class);
    }

    public boolean sendOtp(String username, String code) {
        String url = baseUrl + "/otp/check";
        User body = new User();
        body.setUsername(username);
        body.setCode(code);
        var request = new HttpEntity<>(body);

        ResponseEntity<?> response = rest.postForEntity(url, request, Void.class);
        return response.getStatusCode().isSameCodeAs(HttpStatus.OK);
    }

}
