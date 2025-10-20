package com.tickey.authservice.infrastructre.grpc;

import com.ticket.user_service.proto.UserCredentialRequest;
import com.ticket.user_service.proto.UserCredentialServiceGrpc;
import com.tickey.authservice.domain.model.UserCredential;
import com.tickey.authservice.domain.service.UserCredentialPort;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

@Component
public class UserCredentialGrpcAdapter implements UserCredentialPort {

    @GrpcClient("user-service")
    private UserCredentialServiceGrpc.UserCredentialServiceBlockingStub stub;

    @Override
    public UserCredential findUserCredential(String email) {
        final var request = UserCredentialRequest.newBuilder()
                .setEmail(email)
                .build();

        var response = stub.getUserCredential(request);

        return new UserCredential(response.getEmail(), response.getPassword());
    }
}
