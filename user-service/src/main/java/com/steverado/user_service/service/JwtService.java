package com.steverado.user_service.service;

import com.steverado.user_service.entity.User;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public interface JwtService {

    public String extractUsername(String token);

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    public String generateToken(User user);

    public long getExpirationTime();

    String buildToken(Map<String, Object> extraClaims, User user, long expiration);

    public boolean isTokenValid(String token, UserDetails userDetails);

    boolean isTokenExpired(String token);

    Date extractExpiration(String token);

    Claims extractAllClaims(String token);

    Key getSignInKey();
}
