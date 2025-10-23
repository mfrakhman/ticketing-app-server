package com.mfrakhman.etick.auth.service.impl;

import com.mfrakhman.etick.auth.dto.AuthResponseDto;
import com.mfrakhman.etick.auth.dto.LoginRequestDto;
import com.mfrakhman.etick.auth.dto.RegisterRequestDto;
import com.mfrakhman.etick.auth.service.AuthService;
import com.mfrakhman.etick.auth.utils.JwtUtil;
import com.mfrakhman.etick.user.entity.User;
import com.mfrakhman.etick.user.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public AuthResponseDto register(RegisterRequestDto request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setUserName(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        AuthResponseDto response = new AuthResponseDto();
        response.setUsername(user.getEmail());
        return response;
    }

    @Override
    public AuthResponseDto refreshToken(String refreshToken) {
        if (!jwtUtil.validateRefreshToken(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }
        String email = jwtUtil.extractUsername(refreshToken);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String newAccessToken = jwtUtil.generateAccessToken(email);
        String newRefreshToken = jwtUtil.generateRefreshToken(email);

        AuthResponseDto response = new AuthResponseDto();
        response.setUsername(user.getEmail());
        response.setAccessToken(newAccessToken);
        response.setRefreshToken(newRefreshToken);

        return response;
    }

    @Override
    public AuthResponseDto login(LoginRequestDto request) {
        User findUser = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        String accessToken = jwtUtil.generateAccessToken(findUser.getEmail());
        String refreshToken = jwtUtil.generateRefreshToken(findUser.getEmail());

        AuthResponseDto response = new AuthResponseDto();
        response.setUsername(findUser.getUsername());
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        return response;
    }

}
