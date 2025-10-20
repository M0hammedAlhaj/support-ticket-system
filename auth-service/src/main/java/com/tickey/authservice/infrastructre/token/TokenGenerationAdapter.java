package com.tickey.authservice.infrastructre.token;

import com.tickey.authservice.domain.model.UserCredential;
import com.tickey.authservice.domain.service.TokenGenerationPort;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class TokenGenerationAdapter implements TokenGenerationPort {

    private final KeyGeneration keyGeneration;

    @Override
    public String generateToken(UserCredential userCredential) {
        return Jwts.builder()
                .issuer(userCredential.email())
                .signWith(keyGeneration.getPrivateKey())
                .issuedAt(Date.from(Instant.now().plusSeconds(60 * 15)))
                .compact();
    }
}
