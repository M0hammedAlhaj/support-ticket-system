package com.tickey.authservice.application.register;

public record RegisterUserCommand(String firstName, String lastName, String email, String password,
                                  String confirmPassword, String userType) {
}
