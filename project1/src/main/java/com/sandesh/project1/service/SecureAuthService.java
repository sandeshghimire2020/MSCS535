package com.sandesh.project1.service;

import com.sandesh.project1.dto.AuthResponse;
import com.sandesh.project1.dto.LoginRequest;
import org.springframework.http.ResponseEntity;
import com.sandesh.project1.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.Map;

@Service
public class SecureAuthService {

    private final UserRepository userRepository;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SecureAuthService(UserRepository userRepository, JdbcTemplate jdbcTemplate) {
        this.userRepository = userRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public ResponseEntity<AuthResponse> login(LoginRequest request) {
        return userRepository.findByEmailAndPassword(request.getEmail(), request.getPassword())
                .map(user -> ResponseEntity.ok(new AuthResponse("Login successful", "token")))
                .orElseGet(() -> ResponseEntity.status(401).body(new AuthResponse("Invalid credentials", null)));
    }


    public ResponseEntity<AuthResponse> vulnerableLogin(LoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();
        String sql = "SELECT * FROM users WHERE email = '" + email + "' AND password = '" + password + "'";
        try {
            Map<String, Object> user = jdbcTemplate.queryForMap(sql);
            return ResponseEntity.ok(new AuthResponse("Login successful (vulnerable)", "token"));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new AuthResponse("Invalid credentials (vulnerable)", null));
        }
    }
}
