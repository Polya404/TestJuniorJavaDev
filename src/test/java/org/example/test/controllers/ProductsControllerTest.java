package org.example.test.controllers;

import org.example.test.ProductRequest;
import org.example.test.config.JwtConfig;
import org.example.test.config.JwtTokenProvider;
import org.example.test.services.UserService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductsControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/products";
    }

    @Test
    public void testAddProducts() throws Exception {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setTable("products");
        productRequest.setRecords(Collections.emptyList());
        UserDetails userDetails = userService.getUserByUsername("a");
        String token = jwtTokenProvider.generateToken(userDetails);
        ResponseEntity<String> response = restTemplate.postForEntity(
                getBaseUrl() + "/add",
                new HttpEntity<>(objectMapper.writeValueAsString(productRequest), createJsonHeader(token)),
                String.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Products added successfully", response.getBody());
    }

    @Test
    public void testGetAllProducts() {
        UserDetails userDetails = userService.getUserByUsername("a");
        String token = jwtTokenProvider.generateToken(userDetails);
        HttpEntity<String> entity = new HttpEntity<>(createJsonHeader(token));
        var response = restTemplate.exchange(
                getBaseUrl() + "/all",
                HttpMethod.GET,
                entity,
                List.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private HttpHeaders createJsonHeader(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.setAccept(Collections.singletonList(org.springframework.http.MediaType.APPLICATION_JSON));
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        return headers;
    }
}
