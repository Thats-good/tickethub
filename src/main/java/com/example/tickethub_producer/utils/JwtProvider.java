package com.example.tickethub_producer.utils;

import com.example.tickethub_producer.entity.RedisEntity;
import com.example.tickethub_producer.entity.Ticket;
import com.example.tickethub_producer.entity.enums.Role;
import com.example.tickethub_producer.repository.RedisRepository;
import com.example.tickethub_producer.service.AuthTokens;
import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtProvider {

    @Value("${secret.jwt-secret-key}")
    private String JWT_SECRET_KEY;

    @Value("${secret.jwt-expired-in.access-token}")
    public long JWT_EXPIRED_IN;

    @Value("${secret.jwt-expired-in.refresh-token}")
    @Getter
    private long REFRESH_TOKEN_EXPIRED_IN;

    @Value("${secret.jwt-expired-in.ticket-token}")
    private long TOKEN_EXPIRED_IN;

    public String createTicketToken(Ticket ticket) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(ticket.getSeatNumber()));
        Date now = new Date();
        Date ticketTokenExpiredAt = new Date(now.getTime() + TOKEN_EXPIRED_IN);

        String ticketToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(ticketTokenExpiredAt)
                .claim("ticketId", ticket.getTicketId())
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)
                .compact();
        return ticketToken;
    }

    public AuthTokens createToken(String email, Long userId, Role role) {
        log.info("JWT key={}", JWT_SECRET_KEY);

        Claims claims = Jwts.claims().setSubject(email);
        Date now = new Date();
        Date accessTokenExpiredAt = new Date(now.getTime() + JWT_EXPIRED_IN);
        Date refreshTokenExpiredAt = new Date(now.getTime() + REFRESH_TOKEN_EXPIRED_IN);

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(accessTokenExpiredAt)
                .claim("userId", userId)
                .claim("role", role.name())
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)
                .compact();

        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(refreshTokenExpiredAt)
                .claim("userId", userId)
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)
                .compact();

        return AuthTokens.of(accessToken, refreshToken, JWT_EXPIRED_IN, REFRESH_TOKEN_EXPIRED_IN);
    }

    public boolean isExpiredToken(String token){
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(JWT_SECRET_KEY).build()
                    .parseClaimsJws(token);
            return claims.getBody().getExpiration().after(new Date());

        } catch (ExpiredJwtException e) {
            return true;

        } catch (UnsupportedJwtException e) {
            throw new RuntimeException("JWT is not supported");
        } catch (MalformedJwtException e) {
            throw new RuntimeException("JWT is invalid");
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("JWT has argument error");
        } catch (JwtException e) {
            log.error("[JwtTokenProvider.validateAccessToken]", e);
            throw e;
        }
    }

    public String getEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(JWT_SECRET_KEY).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public long getId(String token) {
        Claims claims = decodeToken(token);
        return claims.get("userId", Long.class);
    }

    public Role getRole(String token) {
        Claims claims = decodeToken(token);

        String roleStr = claims.get("role", String.class);
        try {
            return Role.valueOf(roleStr);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid role in token: " + roleStr);
        }
    }

    public Claims decodeToken(String token) {
        try {
            // 토큰 디코딩 및 검증
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(JWT_SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);

            // 페이로드 반환
            return claimsJws.getBody();
        } catch (SignatureException e) {
            throw new RuntimeException("Invalid JWT signature");
        } catch (Exception e) {
            throw new RuntimeException("Error decoding JWT", e);
        }
    }
}