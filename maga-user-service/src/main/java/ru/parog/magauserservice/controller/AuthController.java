package ru.parog.magauserservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.parog.magauserservice.dto.AuthResponse;
import ru.parog.magauserservice.dto.LoginRequest;
import ru.parog.magauserservice.dto.RegisterRequest;
import ru.parog.magauserservice.dto.TokenValidationResponse;
import ru.parog.magauserservice.service.AuthService;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @GetMapping("/validate")
    public TokenValidationResponse validateToken(@RequestParam String token) {
        return authService.validateToken(token);
    }
}
