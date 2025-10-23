package com.mfrakhman.etick.auth.dto;

import lombok.Data;

@Data
public class TokenRefreshRequestDto {
    String refreshToken;
}
