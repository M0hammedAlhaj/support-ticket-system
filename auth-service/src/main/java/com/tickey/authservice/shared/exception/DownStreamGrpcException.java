package com.tickey.authservice.shared.exception;

import io.grpc.Status;
import lombok.Getter;

@Getter
public class DownStreamGrpcException extends RuntimeException {

    private final Status.Code code;

    public DownStreamGrpcException(Status.Code code, String message) {
        super(message);
        this.code = code;
    }
}
