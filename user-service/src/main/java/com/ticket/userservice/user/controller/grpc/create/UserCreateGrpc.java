package com.ticket.userservice.user.controller.grpc.create;

import com.ticket.user_service.proto.UserCreateRequest;
import com.ticket.user_service.proto.UserCreateResponse;
import com.ticket.user_service.proto.UserCreateServiceGrpc;
import com.ticket.userservice.user.application.create.CreateUserCommand;
import com.ticket.userservice.user.application.create.CreateUserUseCase;
import com.ticket.userservice.user.domain.exception.EmailAlreadyExistsException;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class UserCreateGrpc extends UserCreateServiceGrpc.UserCreateServiceImplBase {

    private final CreateUserUseCase createUserUseCase;

    @Override
    public void createUser(UserCreateRequest request, StreamObserver<UserCreateResponse> responseObserver) {
        try {
            final var command = new CreateUserCommand(request.getFirstName(),
                    request.getLastName(),
                    request.getEmail(),
                    request.getPassword(),
                    request.getUserType());

            createUserUseCase.createUser(command);

            final var response = UserCreateResponse.newBuilder()
                    .setMessage("User created successfully!")
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (EmailAlreadyExistsException e) {
            responseObserver.onError(
                    Status.ALREADY_EXISTS
                            .withDescription(e.getMessage())
                            .asRuntimeException()
            );
        }
    }
}

