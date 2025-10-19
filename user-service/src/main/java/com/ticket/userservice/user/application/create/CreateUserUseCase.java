package com.ticket.userservice.user.application.create;

import com.ticket.userservice.user.domain.entity.User;
import com.ticket.userservice.user.domain.exception.EmailAlreadyExistsException;
import com.ticket.userservice.user.domain.repository.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase {

    private final UserRepository userRepository;
    private final Validator validate;

    public User createUser(CreateUserCommand createUserCommand) {
        validationCommand(createUserCommand);

        var email = createUserCommand.getEmail();
        if (userRepository.existByEmail(email)) {
            throw new EmailAlreadyExistsException(email);
        }

        var user = new User();
        user.setUuid(UUID.randomUUID());
        user.setFirstName(createUserCommand.getFirstName());
        user.setLastName(createUserCommand.getLastName());
        user.setEmail(email);
        user.setPassword(createUserCommand.getPassword());
        user.setUserType(createUserCommand.getUserType());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);
        return user;
    }

    private void validationCommand(CreateUserCommand createUserCommand) {
        Set<ConstraintViolation<CreateUserCommand>> violations = validate.validate(createUserCommand);
        if (!violations.isEmpty()) {
            String errors = violations.stream()
                    .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                    .collect(Collectors.joining(", "));
            throw new ValidationException(errors);
        }
    }
}
