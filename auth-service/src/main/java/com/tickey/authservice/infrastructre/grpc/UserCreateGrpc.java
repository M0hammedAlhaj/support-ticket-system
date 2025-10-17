package com.tickey.authservice.infrastructre.grpc;

import com.ticket.user_service.proto.UserCreateRequest;
import com.ticket.user_service.proto.UserCreateServiceGrpc;
import com.tickey.authservice.domain.register.model.UserAuth;
import com.tickey.authservice.domain.register.service.UserCreate;
import com.tickey.authservice.shared.DownstreamGrpcException;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.springframework.stereotype.Component;

@Component
public class UserCreateGrpc implements UserCreate {

    private final UserCreateServiceGrpc.UserCreateServiceBlockingStub stub;

    public UserCreateGrpc() {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("user-service", 9090)
                .usePlaintext()
                .build();
        this.stub = UserCreateServiceGrpc.newBlockingStub(channel);
    }

    @Override
    public String createUser(UserAuth userAuth) {
        try {
            final var request = UserCreateRequest.newBuilder()
                    .setFirstName(userAuth.firstName())
                    .setLastName(userAuth.lastName())
                    .setEmail(userAuth.email())
                    .setPassword(userAuth.password())
                    .setUserType(userAuth.userType())
                    .build();
            var response = stub.createUser(request);
            return response.getMessage();
        } catch (StatusRuntimeException e) {
            throw new DownstreamGrpcException(
                    e.getStatus().getCode(),
                    e.getStatus().getDescription()
            );
        }
    }
}
