package com.nqbao.project.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Service
public class TokenService {
    private final byte[] ENC_B64_SECRET_KEY = Base64.getEncoder().encode("wBt6PZGCE7pBi6HV/wEZKNvg9xwZ2sOswFRAJWZEhKTjYRsiNYgIYfHan/wdoIUgSnvkq8mFtsMSxo+On4r210cGxJeKHqcNDj8sDHLsyDg=\n".getBytes());
    private final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(ENC_B64_SECRET_KEY);

    private final Long TOKEN_EXPIRATION = 24L * 60L * 60L * 1000L;

    public String generateToken(UserDetails user) {
        return Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION))
                .signWith(SECRET_KEY).compact();
    }

    public Claims extractToken(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build().parseSignedClaims(token).getPayload();
    }
}
