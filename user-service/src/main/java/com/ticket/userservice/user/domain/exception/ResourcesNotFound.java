package com.ticket.userservice.user.domain.exception;

public class ResourcesNotFound extends RuntimeException {
    public ResourcesNotFound(String message) {
        super(message);
    }
}
