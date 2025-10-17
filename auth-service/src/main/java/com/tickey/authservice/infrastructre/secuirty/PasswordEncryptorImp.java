package com.tickey.authservice.infrastructre.secuirty;

import com.tickey.authservice.domain.register.service.PasswordEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncryptorImp implements PasswordEncryptor {

    private PasswordEncoder passwordEncoder;

    @Override
    public String encode(CharSequence rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
