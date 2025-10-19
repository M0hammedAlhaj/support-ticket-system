package com.ticket.userservice.user.controller.grpc.get;

import com.ticket.user_service.proto.UserCredentialRequest;
import com.ticket.user_service.proto.UserCredentialResponse;
import com.ticket.user_service.proto.UserCredentialServiceGrpc;
import com.ticket.userservice.user.application.get.GetUserCredentialCommand;
import com.ticket.userservice.user.application.get.GetUserCredentialUseCase;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@AllArgsConstructor
public class UserCredentialGrpc extends UserCredentialServiceGrpc.UserCredentialServiceImplBase {

    private GetUserCredentialUseCase useCase;

    @Override
    public void getUserCredential(UserCredentialRequest request, StreamObserver<UserCredentialResponse> responseObserver) {
        final var command = new GetUserCredentialCommand(request.getEmail());
        final var credential = useCase.findUserCredentialByEmail(command);

        final var userCredentialResponse = UserCredentialResponse.newBuilder()
                .setEmail(credential.email())
                .setPassword(credential.password())
                .build();
        responseObserver.onNext(userCredentialResponse);
        responseObserver.onCompleted();
    }
}
