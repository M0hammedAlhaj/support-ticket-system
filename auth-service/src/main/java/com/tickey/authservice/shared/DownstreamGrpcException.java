package com.tickey.authservice.shared;

import io.grpc.Status;
import lombok.Getter;

@Getter
public class DownstreamGrpcException extends RuntimeException {

    private final Status.Code code;

    public DownstreamGrpcException(Status.Code code, String message) {
        super(message);
        this.code = code;
    }
}
