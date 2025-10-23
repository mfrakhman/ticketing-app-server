package com.mfrakhman.etick.auth.service;

import com.mfrakhman.etick.auth.dto.AuthResponseDto;
import com.mfrakhman.etick.auth.dto.LoginRequestDto;
import com.mfrakhman.etick.auth.dto.RegisterRequestDto;

public interface AuthService {
    AuthResponseDto login(LoginRequestDto request);

    AuthResponseDto register(RegisterRequestDto request);

    AuthResponseDto refreshToken(String refreshToken);
}
