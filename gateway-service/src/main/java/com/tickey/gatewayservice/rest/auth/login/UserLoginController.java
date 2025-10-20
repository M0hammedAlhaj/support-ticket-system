package com.tickey.gatewayservice.rest.auth.login;

import com.ticket.grpc.LoginRequest;
import com.tickey.gatewayservice.grpc.UserLoginGrpc;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class UserLoginController {

    private final UserLoginGrpc grpc;

    public UserLoginController(UserLoginGrpc grpc) {
        this.grpc = grpc;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest request) {
        var requestGrpc = LoginRequest.newBuilder()
                .setEmail(request.email())
                .setPassword(request.password())
                .build();

        var result = grpc.login(requestGrpc);

        return ResponseEntity.ok(result);
    }
}
