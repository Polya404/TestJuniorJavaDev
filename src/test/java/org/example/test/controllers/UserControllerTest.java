package org.example.test.controllers;

import org.example.test.models.User;
import org.example.test.modelsForTest.UserModel;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
public class UserControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String getBaseUrl() {
        return "http://localhost:" + port + "/user";
    }

    @Test
    public void testAddUser() throws Exception {
        UserModel user = new UserModel("test", "test", "test");
        ResponseEntity<String> response = restTemplate.postForEntity(
                getBaseUrl() + "/add",
                new HttpEntity<>(objectMapper.writeValueAsString(user), createJsonHeader()),
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo("User created successfully");
    }

    @Test
    public void testAuthenticateUser() throws Exception {
        UserModel user = new UserModel("a", "a", "a");
        ResponseEntity<String> response = restTemplate.postForEntity(
                getBaseUrl() + "/authenticate",
                new HttpEntity<>(objectMapper.writeValueAsString(user), createJsonHeader()),
                String.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private HttpHeaders createJsonHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.setAccept(Collections.singletonList(org.springframework.http.MediaType.APPLICATION_JSON));
        return headers;
    }
}
