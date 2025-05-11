package ru.parog.magauserservice.service;


import ru.parog.magauserservice.dto.AuthResponse;
import ru.parog.magauserservice.dto.LoginRequest;
import ru.parog.magauserservice.dto.RegisterRequest;
import ru.parog.magauserservice.dto.TokenValidationResponse;

public interface AuthService {

    public AuthResponse register(RegisterRequest request);

    public AuthResponse login(LoginRequest request);

    public TokenValidationResponse validateToken(String token);
}
