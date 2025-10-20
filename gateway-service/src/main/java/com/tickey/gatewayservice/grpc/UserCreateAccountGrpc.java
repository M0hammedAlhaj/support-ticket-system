package com.tickey.gatewayservice.grpc;

import com.ticket.grpc.RegisterGrpcGrpc;
import com.ticket.grpc.RegistrationRequest;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

@Component
public class UserCreateAccountGrpc {

    @GrpcClient("auth-service")
    private RegisterGrpcGrpc.RegisterGrpcBlockingStub stub;

    public String createAccount(RegistrationRequest request) {
        final var response = stub.registration(request);
        return response.getMessage();
    }
}

