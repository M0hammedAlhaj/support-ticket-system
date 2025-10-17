package com.tickey.gatewayservice.rest.auth.register;

import com.ticket.grpc.RegistrationRequest;
import com.tickey.gatewayservice.grpc.UserCreateAccountGrpc;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class UserRegisterController {

    private final UserCreateAccountGrpc userCreateAccountGrpc;

    public UserRegisterController(UserCreateAccountGrpc userCreateAccountGrpc) {
        this.userCreateAccountGrpc = userCreateAccountGrpc;
    }

    @PostMapping("/register")
    public ResponseEntity<String> invoke(@RequestBody UserRegisterRequest request) {
        final var requestGrpc = RegistrationRequest.newBuilder()
                .setFirstName(request.firstName())
                .setLastName(request.lastName())
                .setEmail(request.email())
                .setPassword(request.password())
                .setConfirmPassword(request.passwordConfirm())
                .setUserType(request.userType())
                .build();

        final var message = userCreateAccountGrpc.createAccount(requestGrpc);

        return ResponseEntity.ok().body(message);
    }
}
