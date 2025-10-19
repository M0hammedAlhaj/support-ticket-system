package com.tickey.authservice.infrastructre.token;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.security.*;

@Component
@Getter
public class KeyGeneration {

    private PrivateKey privateKey;

    private PublicKey publicKey;

    public KeyGeneration() {
        var keyPair = generateKey();
        privateKey = keyPair.getPrivate();
        publicKey = keyPair.getPublic();
    }

    public static KeyPair generateKey() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }
}
