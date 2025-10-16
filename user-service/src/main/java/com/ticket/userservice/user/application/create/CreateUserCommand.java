package com.ticket.userservice.user.application.create;

import com.ticket.userservice.user.domain.model.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateUserCommand {

    @NotBlank(message = "First name cannot be blank")
    private final String firstName;

    @NotBlank(message = "Last name cannot be blank")
    private final String lastName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be valid")
    private final String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private final String password;

    @NotNull(message = "UserType cannot be null")
    private final UserType userType;

    public CreateUserCommand(String firstName, String lastName, String email, String password, String userType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.userType = UserType.fromString(userType);
    }
}