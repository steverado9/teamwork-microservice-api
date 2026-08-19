package com.steverado9.gif_service.service.Impl;

import com.steverado9.gif_service.service.JwtService;
import io.jsonwebtoken.Claims;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

public class JwtServiceImpl implements JwtService {
    @Override
    public Long extractUserId(String token) {
        return 0L;
    }

    @Override
    public String extractUsername(String token) {
        return "";
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return null;
    }

    @Override
    public boolean isTokenValid(String token) {
        return false;
    }

    @Override
    public boolean isTokenExpired(String token) {
        return false;
    }

    @Override
    public Date extractExpiration(String token) {
        return null;
    }

    @Override
    public Claims extractAllClaims(String token) {
        return null;
    }

    @Override
    public Key getSignInKey() {
        return null;
    }
}
