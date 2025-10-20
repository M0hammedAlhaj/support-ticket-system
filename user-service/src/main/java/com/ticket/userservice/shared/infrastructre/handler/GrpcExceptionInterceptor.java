package com.ticket.userservice.shared.infrastructre.handler;

import com.ticket.userservice.user.domain.exception.ResourcesNotFoundException;
import io.grpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GrpcExceptionInterceptor implements ServerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(GrpcExceptionInterceptor.class);


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
                } catch (ResourcesNotFoundException e) {
                    call.close(
                            Status.NOT_FOUND.withDescription(e.getMessage()),
                            new Metadata()
                    );
                }
            }
        };
    }
}
