package com.tickey.authservice.infrastructre.grpc;

import com.ticket.user_service.proto.UserCreateRequest;
import com.ticket.user_service.proto.UserCreateServiceGrpc;
import com.tickey.authservice.domain.model.NewUser;
import com.tickey.authservice.domain.service.UserCreatePort;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Component;

@Component
public class UserCreateGrpcAdapter implements UserCreatePort {

    private final UserCreateServiceGrpc.UserCreateServiceBlockingStub stub;

    public UserCreateGrpcAdapter() {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("user-service", 9090)
                .usePlaintext()
                .build();
        this.stub = UserCreateServiceGrpc.newBlockingStub(channel);
    }

    @Override
    public String createUser(NewUser newUser) {

        final var request = UserCreateRequest.newBuilder()
                .setFirstName(newUser.firstName())
                .setLastName(newUser.lastName())
                .setEmail(newUser.email())
                .setPassword(newUser.password())
                .setUserType(newUser.userType())
                .build();
        var response = stub.createUser(request);

        return response.getMessage();
    }
}
