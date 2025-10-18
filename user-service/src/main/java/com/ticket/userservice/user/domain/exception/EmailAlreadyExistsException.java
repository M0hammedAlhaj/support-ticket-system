package com.ticket.userservice.user.domain.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String email) {
        super(String.format("A user with email %s already exists", email));
    }
}
