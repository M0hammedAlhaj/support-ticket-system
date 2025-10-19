package com.tickey.authservice.infrastructre.grpc;

import com.ticket.user_service.proto.UserCredentialRequest;
import com.ticket.user_service.proto.UserCredentialServiceGrpc;
import com.tickey.authservice.domain.model.UserCredential;
import com.tickey.authservice.domain.service.UserCredentialPort;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Component;

@Component
public class UserCredentialGrpcAdapter implements UserCredentialPort {

    private final UserCredentialServiceGrpc.UserCredentialServiceBlockingStub stub;

    public UserCredentialGrpcAdapter() {
        var managedChannel = ManagedChannelBuilder
                .forAddress("user-service", 9090)
                .usePlaintext()
                .build();
        this.stub = UserCredentialServiceGrpc.newBlockingStub(managedChannel);
    }


    @Override
    public UserCredential findUserCredential(String email) {
        final var request = UserCredentialRequest.newBuilder()
                .setEmail(email)
                .build();

        var response = stub.getUserCredential(request);

        return new UserCredential(response.getEmail(), response.getPassword());
    }
}
