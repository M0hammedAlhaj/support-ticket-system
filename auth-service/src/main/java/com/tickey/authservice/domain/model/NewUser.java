package com.tickey.authservice.domain.model;

public record NewUser(String firstName, String lastName, String email, String password, String userType) {
}
