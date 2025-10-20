package com.tickey.gatewayservice.rest.auth.login;

public record UserLoginRequest(String email, String password) {
}
