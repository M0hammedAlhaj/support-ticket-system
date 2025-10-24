package com.tickey.gatewayservice.shared;

import io.grpc.StatusRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerException {

    private static final Logger log = LoggerFactory.getLogger(GlobalHandlerException.class);

    @ExceptionHandler(StatusRuntimeException.class)
    public ResponseEntity<String> handleGrpcException(StatusRuntimeException ex) {
        log.warn("gRPC exception handled: code={}, description={}",
                ex.getStatus().getCode(), ex.getStatus().getDescription(), ex);

        return switch (ex.getStatus().getCode()) {
            case INVALID_ARGUMENT -> ResponseEntity.badRequest().body(ex.getStatus().getDescription());
            case ALREADY_EXISTS -> ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getStatus().getDescription());
            case NOT_FOUND -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getStatus().getDescription());
            case UNAUTHENTICATED ->
                    ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getStatus().getDescription());
            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        };
    }
}
