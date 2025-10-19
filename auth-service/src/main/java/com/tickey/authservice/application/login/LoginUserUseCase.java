package com.tickey.authservice.application.login;

import com.tickey.authservice.domain.exception.CredentialInvalidException;
import com.tickey.authservice.domain.service.PasswordEncryptor;
import com.tickey.authservice.domain.service.TokenGenerationPort;
import com.tickey.authservice.domain.service.UserCredentialPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUserUseCase {

    private final PasswordEncryptor passwordEncryptor;

    private final UserCredentialPort userCredentialPort;

    private final TokenGenerationPort tokenGenerationPort;

    public String execute(LoginUserCommand loginUserCommand) {
        final var credential = userCredentialPort.findUserCredential(loginUserCommand.email());
        if (!passwordEncryptor.matches(credential.password(), loginUserCommand.password())) {
            throw new CredentialInvalidException();
        }

        return tokenGenerationPort.generateToken(credential);
    }
}
