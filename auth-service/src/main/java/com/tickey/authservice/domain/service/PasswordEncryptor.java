package com.tickey.authservice.domain.service;

public interface PasswordEncryptor {

    String encode(CharSequence rawPassword);

    boolean matches(CharSequence rawPassword, String encodedPassword);
}
