package com.tickey.authservice.controller.register;

import com.ticket.grpc.RegisterGrpcGrpc;
import com.ticket.grpc.RegistrationRequest;
import com.ticket.grpc.RegistrationResponse;
import com.tickey.authservice.application.register.RegisterUserCommand;
import com.tickey.authservice.application.register.RegisterUserUseCase;
import com.tickey.authservice.domain.register.exception.PasswordMismatchException;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class UserRegisterController extends RegisterGrpcGrpc.RegisterGrpcImplBase {

    private final RegisterUserUseCase useCase;

    @Override
    public void registration(RegistrationRequest request, StreamObserver<RegistrationResponse> responseObserver) {

        final var command = new RegisterUserCommand(request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword(),
                request.getConfirmPassword(),
                request.getUserType());
        try {
            final var message = useCase.execute(command);

            final var response = RegistrationResponse.newBuilder()
                    .setMessage(message)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (PasswordMismatchException e) {
            responseObserver.onError(
                    Status.INVALID_ARGUMENT
                            .withDescription(e.getMessage())
                            .asRuntimeException()
            );
        }
    }
}
