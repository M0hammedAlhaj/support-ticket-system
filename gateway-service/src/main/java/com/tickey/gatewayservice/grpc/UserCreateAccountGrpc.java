package com.tickey.gatewayservice.grpc;

import com.ticket.grpc.RegisterGrpcGrpc;
import com.ticket.grpc.RegistrationRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCreateAccountGrpc {

    private final RegisterGrpcGrpc.RegisterGrpcBlockingStub stub;

    public UserCreateAccountGrpc() {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("auth-service", 9090)
                .usePlaintext()
                .build();

        this.stub = RegisterGrpcGrpc.newBlockingStub(channel);
    }

    public String createAccount(RegistrationRequest request) {
        final var response = stub.registration(request);
        return response.getMessage();
    }
}

