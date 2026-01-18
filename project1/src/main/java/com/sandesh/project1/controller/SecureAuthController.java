package com.sandesh.project1.controller;

import com.sandesh.project1.dto.AuthResponse;
import com.sandesh.project1.dto.LoginRequest;
import com.sandesh.project1.service.SecureAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Secure Authentication")
@RestController
@RequestMapping("/api/v1/secure-auth")
public class SecureAuthController {

    private final SecureAuthService secureAuthService;

    public SecureAuthController(SecureAuthService secureAuthService) {
        this.secureAuthService = secureAuthService;
    }

    @Operation(
        summary = "Secure Login",
        description = "Authenticate securely with email and password. Prevents SQL injection.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
        }
    )
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return secureAuthService.login(request);
    }

    @Operation(
        summary = "Vulnerable Login",
        description = "Demonstrates a login endpoint vulnerable to SQL injection.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Login successful (vulnerable)"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials (vulnerable)")
        }
    )
    @PostMapping("/vulnerable-login")
    public ResponseEntity<AuthResponse> vulnerableLogin(@RequestBody LoginRequest request) {
        return secureAuthService.vulnerableLogin(request);
    }
}
