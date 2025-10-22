package com.tickey.authservice.controller;

import com.ticket.grpc.LoginGrpcGrpc;
import com.ticket.grpc.LoginRequest;
import com.ticket.grpc.LoginResponse;
import com.tickey.authservice.application.login.LoginUserCommand;
import com.tickey.authservice.application.login.LoginUserUseCase;
import com.tickey.authservice.domain.exception.CredentialInvalidException;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class UserLoginController extends LoginGrpcGrpc.LoginGrpcImplBase {

    private final LoginUserUseCase useCase;

    @Override
    public void login(LoginRequest request, StreamObserver<LoginResponse> responseObserver) {
        final var command = new LoginUserCommand(request.getEmail(), request.getPassword());
        try {
            final var result = useCase.execute(command);
            var response = LoginResponse.newBuilder()
                    .setToken(result)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (CredentialInvalidException e) {
            responseObserver.onError(
                    Status.UNAUTHENTICATED
                            .withDescription("Invalid email or password")
                            .asRuntimeException()
            );
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.NOT_FOUND) {
                responseObserver.onError(Status.UNAUTHENTICATED
                        .withDescription("Invalid email or password ")
                        .asRuntimeException());
            }
        }
    }
}
