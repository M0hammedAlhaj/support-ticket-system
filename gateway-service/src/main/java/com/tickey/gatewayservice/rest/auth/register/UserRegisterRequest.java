package com.tickey.gatewayservice.rest.auth.register;

public record UserRegisterRequest(String firstName, String lastName, String email, String password,
                                  String passwordConfirm, String userType) {
}
