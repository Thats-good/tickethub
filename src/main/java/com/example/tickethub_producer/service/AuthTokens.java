package com.example.tickethub_producer.service;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthTokens {

    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiresIn;
    private Long refreshTokenExpiresIn;

    public static AuthTokens of(String accessToken, String refreshToken, Long accessTokenExpiresIn, Long refreshTokenExpiresIn) {
        return new AuthTokens(accessToken, refreshToken, accessTokenExpiresIn, refreshTokenExpiresIn);
    }
}
