package com.ticket.userservice.shared.infrastructre.handler;

import io.grpc.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ValidationInterceptor implements ServerInterceptor {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call,
            Metadata headers,
            ServerCallHandler<ReqT, RespT> next) {

        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<>(
                next.startCall(call, headers)) {

            @Override
            public void onMessage(ReqT message) {

                if (message != null) {
                    Set<ConstraintViolation<ReqT>> violations = validator.validate(message);
                    if (!violations.isEmpty()) {
                        String errorMessage = violations.stream()
                                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                                .collect(Collectors.joining(", "));

                        call.close(Status.INVALID_ARGUMENT
                                .withDescription(errorMessage)
                                .asRuntimeException().getStatus(), new Metadata());
                        return;
                    }
                }
                super.onMessage(message);
            }
        };
    }
}
