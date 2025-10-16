package com.ticket.userservice.user.controller.grpc.create;

import com.ticket.user_service.proto.UserCreateRequest;
import com.ticket.user_service.proto.UserCreateResponse;
import com.ticket.user_service.proto.UserCreateServiceGrpc;
import com.ticket.userservice.user.application.create.CreateUserCommand;
import com.ticket.userservice.user.application.create.CreateUserUseCase;
import com.ticket.userservice.user.domain.exception.EmailAlreadyExistsException;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class UserCreateGrpc extends UserCreateServiceGrpc.UserCreateServiceImplBase {

    private final CreateUserUseCase createUserUseCase;

    @Override
    public void createUser(UserCreateRequest request, StreamObserver<UserCreateResponse> responseObserver) {
        final var command = new CreateUserCommand(request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword(),
                request.getUserType());
        try {
            createUserUseCase.createUser(command);

            final var response = UserCreateResponse.newBuilder()
                    .setMessage("User created successfully!")
                    .setHttpStatus(201)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (EmailAlreadyExistsException e) {
            final var response = UserCreateResponse.newBuilder()
                    .setMessage(e.getMessage())
                    .setHttpStatus(409)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            final var response = UserCreateResponse.newBuilder()
                    .setMessage(e.getMessage())
                    .setHttpStatus(500)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}
