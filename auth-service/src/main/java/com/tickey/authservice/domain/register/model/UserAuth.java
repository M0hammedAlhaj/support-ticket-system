package com.tickey.authservice.domain.register.model;

public record UserAuth(String firstName, String lastName, String email, String password, String userType) {
}
