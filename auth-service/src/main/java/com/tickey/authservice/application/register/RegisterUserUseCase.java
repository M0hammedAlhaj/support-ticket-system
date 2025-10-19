package com.tickey.authservice.application.register;

import com.tickey.authservice.domain.exception.PasswordMismatchException;
import com.tickey.authservice.domain.model.NewUser;
import com.tickey.authservice.domain.service.PasswordEncryptor;
import com.tickey.authservice.domain.service.UserCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterUserUseCase {

    private final UserCreate userCreate;
    private final PasswordEncryptor passwordEncryptor;

    public String execute(RegisterUserCommand command) {
        final var password = command.password();
        final var passwordConfirm = command.confirmPassword();

        if (!password.equals(passwordConfirm)) {
            throw new PasswordMismatchException();
        }

        final var encodedPassword = passwordEncryptor.encode(password);
        final var userAuth = new NewUser(command.firstName(),
                command.lastName(),
                command.email(),
                encodedPassword,
                command.userType());

        return userCreate.createUser(userAuth);
    }
}
