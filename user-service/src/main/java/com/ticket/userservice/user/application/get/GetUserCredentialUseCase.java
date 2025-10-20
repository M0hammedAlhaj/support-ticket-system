package com.ticket.userservice.user.application.get;

import com.ticket.userservice.user.domain.exception.ResourcesNotFoundException;
import com.ticket.userservice.user.domain.model.UserCredential;
import com.ticket.userservice.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUserCredentialUseCase {

    private final UserRepository userRepository;

    public UserCredential findUserCredentialByEmail(GetUserCredentialCommand command) {
        final var email = command.email();
        final var user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new ResourcesNotFoundException("User not found by Email: " + email);
        }
        return user.get();
    }
}
