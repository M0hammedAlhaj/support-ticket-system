package com.tickey.gatewayservice.shared;

import io.grpc.StatusRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(StatusRuntimeException.class)
    public ResponseEntity<String> handleGrpcException(StatusRuntimeException ex) {
        return switch (ex.getStatus().getCode()) {
            case INVALID_ARGUMENT -> ResponseEntity.badRequest().body(ex.getStatus().getDescription());
            case ALREADY_EXISTS -> ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getStatus().getDescription());
            case NOT_FOUND -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getStatus().getDescription());
            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal error");
        };
    }
}
