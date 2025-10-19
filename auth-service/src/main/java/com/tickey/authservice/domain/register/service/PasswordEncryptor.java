package com.tickey.authservice.domain.register.service;

public interface PasswordEncryptor {

    String encode(CharSequence rawPassword);

    boolean matches(CharSequence rawPassword, String encodedPassword);
}
