package com.tickey.authservice.domain.register.exception;

public class PasswordMismatchException extends RuntimeException {
    public PasswordMismatchException() {
        super("Password and confirmation password do not match.");
    }
}
