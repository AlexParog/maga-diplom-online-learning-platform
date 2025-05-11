package ru.parog.magauserservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.parog.magauserservice.dto.AuthResponse;
import ru.parog.magauserservice.dto.LoginRequest;
import ru.parog.magauserservice.dto.RegisterRequest;
import ru.parog.magauserservice.dto.TokenValidationResponse;
import ru.parog.magauserservice.entity.Role;
import ru.parog.magauserservice.entity.User;
import ru.parog.magauserservice.exception.UserAlreadyExistsException;
import ru.parog.magauserservice.exception.UserNotFoundException;
import ru.parog.magauserservice.mapper.UserMapper;
import ru.parog.magauserservice.repository.RoleRepository;
import ru.parog.magauserservice.repository.UserRepository;
import ru.parog.magauserservice.security.JwtService;
import ru.parog.magauserservice.service.AuthService;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("Пользователь с таким именем уже существует");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Пользователь с таким email уже существует");
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Добавляем роль ROLE_USER
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Роль не найдена"));
        user.setRoles(Collections.singleton(userRole));

        user = userRepository.save(user);

        String token = jwtService.generateToken(createUserDetails(user));
        String refreshToken = jwtService.generateRefreshToken(createUserDetails(user));

        return AuthResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = findUserByUsername(request.getUsername());

        String token = jwtService.generateToken(createUserDetails(user));
        String refreshToken = jwtService.generateRefreshToken(createUserDetails(user));

        return AuthResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional(readOnly = true)
    public TokenValidationResponse validateToken(String token) {
        try {
            String username = jwtService.extractUsername(token);
            User user = findUserByUsername(username);

            UserDetails userDetails = createUserDetails(user);
            boolean isValid = jwtService.isTokenValid(token, userDetails);

            if (!isValid) {
                return TokenValidationResponse.builder()
                        .isValid(false)
                        .build();
            }

            Set<String> roles = user.getRoles().stream()
                    .map(Role::getName)
                    .collect(Collectors.toSet());

            return TokenValidationResponse.builder()
                    .isValid(true)
                    .userId(user.getId())
                    .username(user.getUsername())
                    .roles(roles)
                    .build();
        } catch (Exception e) {
            return TokenValidationResponse.builder()
                    .isValid(false)
                    .build();
        }
    }

    private UserDetails createUserDetails(User user) {
        var authorities = user.getRoles().stream()
                .map(role -> new org.springframework.security.core.authority.SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isActive(),
                true,
                true,
                true,
                authorities
        );
    }

    private User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с username={0} не найден", username));
    }
}
