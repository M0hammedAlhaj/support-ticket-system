package com.tickey.authservice.domain.exception;

public class CredentialInvalidException extends RuntimeException {
    public CredentialInvalidException() {
        super("Invalid email or password");
    }
}
