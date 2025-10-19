package com.tickey.authservice.shared.handler;

import com.tickey.authservice.shared.exception.DownStreamGrpcException;
import io.grpc.*;
import org.springframework.stereotype.Component;

@Component
public class GrpcHandlerException implements ServerInterceptor {

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call,
            Metadata headers,
            ServerCallHandler<ReqT, RespT> next) {

        ServerCall.Listener<ReqT> listener = next.startCall(call, headers);

        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<>(listener) {

            @Override
            public void onHalfClose() {
                try {
                    super.onHalfClose();
                } catch (DownStreamGrpcException ex) {
                    call.close(
                            Status.fromCodeValue(ex.getCode().value())
                                    .withDescription(ex.getMessage())
                                    .withCause(ex),
                            new Metadata()
                    );
                } catch (Exception ex) {
                    call.close(
                            Status.INTERNAL.withDescription("Internal server error"),
                            new Metadata()
                    );
                }
            }
        };
    }
}
