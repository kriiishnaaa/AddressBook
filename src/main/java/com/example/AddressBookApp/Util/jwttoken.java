package com.example.AddressBookApp.Util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class jwttoken {

    @Value("${jwt.secret}")
    private String TOKEN_SECRET;

    private static final long ACCESS_TOKEN_EXPIRATION = 60000; // 1 minute (60,000 ms)
    private static final long REFRESH_TOKEN_EXPIRATION = 604800000; // 7 days
    private static final String ISSUER = "AddressBookApp";

    // Generate JWT Access Token
    public String createToken(Long id)   {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);

            long expirationMillis = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(1);

            String token = JWT.create()
                    .withClaim("user_id", id)
                    .withExpiresAt(new Date(expirationMillis))
                    .sign(algorithm);

            return token;

        } catch (JWTCreationException exception) {
            exception.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Generate JWT Refresh Token
    public String createRefreshToken(Long userId) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withClaim("user_id", userId)
                    .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error creating refresh token: " + exception.getMessage());
        }
    }

    // Decode & Verify JWT Token to get User ID
    public Long decodeToken(String token) {
        try {
            com.auth0.jwt.JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getClaim("user_id").asLong();
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Invalid or expired token.");
        }
    }

    // Verify if Token is Valid
    public boolean isTokenValid(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET))
                    .withIssuer(ISSUER)
                    .build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    // Check if Token is Expired
    public boolean isTokenExpired(String token) {
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            return decodedJWT.getExpiresAt().before(new Date());
        } catch (Exception e) {
            return true; // Treat invalid token as expired
        }
    }
}

