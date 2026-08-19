package com.steverado.article_service.service;

import io.jsonwebtoken.Claims;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

public interface JwtService {

    public Long extractUserId(String token);

    public String extractUsername(String token);

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    public boolean isTokenValid(String token);

    boolean isTokenExpired(String token);

    Date extractExpiration(String token);

    Claims extractAllClaims(String token);

    Key getSignInKey();
}
