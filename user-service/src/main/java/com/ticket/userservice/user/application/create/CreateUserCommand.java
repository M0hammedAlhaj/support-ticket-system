package com.ticket.userservice.user.application.create;

import com.ticket.userservice.user.domain.model.UserType;

public record CreateUserCommand(
        String firstName,
        String lastName,
        String email,
        String password,
        UserType userType
) {
}
