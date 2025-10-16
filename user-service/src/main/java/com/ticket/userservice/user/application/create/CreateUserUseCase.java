package com.ticket.userservice.user.application.create;

import com.ticket.userservice.user.domain.entity.User;
import com.ticket.userservice.user.domain.exception.EmailAlreadyExistsException;
import com.ticket.userservice.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase {

    private final UserRepository userRepository;

    public User createUser(CreateUserCommand createUserCommand) {
        var email = createUserCommand.email();
        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyExistsException(email);
        }

        var user = new User();
        user.setUuid(UUID.randomUUID());
        user.setFirstName(createUserCommand.firstName());
        user.setLastName(createUserCommand.lastName());
        user.setEmail(createUserCommand.email());
        user.setPassword(createUserCommand.password());
        user.setUserType(createUserCommand.userType());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);
        return user;
    }
}
