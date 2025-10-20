package com.tickey.authservice.shared.handler;

import io.grpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GrpcHandlerException implements ServerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(GrpcHandlerException.class);


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
                } catch (StatusRuntimeException ex) {
                    call.close(
                            ex.getStatus(),
                            new Metadata()
                    );
                } catch (Exception ex) {
                    log.error("Unexpected internal error in gRPC call {}: {}",
                            call.getMethodDescriptor().getFullMethodName(), ex.getMessage(), ex);
                    call.close(
                            Status.INTERNAL.withDescription("Internal server error"),
                            new Metadata()
                    );
                }
            }
        };
    }
}
