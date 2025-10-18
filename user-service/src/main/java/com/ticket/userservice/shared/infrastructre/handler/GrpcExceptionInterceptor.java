package com.ticket.userservice.shared.infrastructre.handler;

import io.grpc.*;
import org.springframework.stereotype.Component;

@Component
public class GrpcExceptionInterceptor implements ServerInterceptor {
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
                } catch (jakarta.validation.ValidationException ex) {
                    call.close(
                            Status.INVALID_ARGUMENT.withDescription(ex.getMessage()),
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
