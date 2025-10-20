package com.tickey.gatewayservice.grpc;

import com.ticket.grpc.LoginGrpcGrpc;
import com.ticket.grpc.LoginRequest;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

@Component
public class UserLoginGrpc {

    @GrpcClient("auth-service")
    private LoginGrpcGrpc.LoginGrpcBlockingStub stub;

    public String login(LoginRequest request) {
        final var response = stub.login(request);
        return response.getToken();
    }
}
