package com.mfrakhman.etick.auth.dto;

import lombok.Data;

@Data
public class AuthResponseDto {
    private String username;
    private String accessToken;
    private String refreshToken;
}
