package com.example.SecurityApp.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;


@Component
public class JWTUtil {

    @Value("${jwt_secret}")
    private String secretKey;

    public String generateToken(String username) {
        Instant expirationInstant = ZonedDateTime.now().plusMinutes(60).toInstant();
        Date expirationDate = Date.from(expirationInstant);

        return JWT.create()
                .withSubject("User details")
                .withClaim("username", username)
                .withIssuedAt(new Date())
                .withIssuer("admin")
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secretKey));
    }

    public String validateTokenAndRetrieveClaim(String token) {
        JWTVerifier jwtVerifier =  JWT.require(Algorithm.HMAC256(secretKey))
                .withSubject("User details")
                .withIssuer("admin")
                .build();
        DecodedJWT jwt = jwtVerifier.verify(token);
        return jwt.getClaim("username").asString();
    }
}
