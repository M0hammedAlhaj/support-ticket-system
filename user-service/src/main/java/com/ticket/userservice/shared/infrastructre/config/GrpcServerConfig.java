package com.ticket.userservice.shared.infrastructre.config;

import com.ticket.userservice.shared.infrastructre.handler.GrpcExceptionInterceptor;
import net.devh.boot.grpc.server.serverfactory.GrpcServerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcServerConfig {

    @Bean
    public GrpcServerConfigurer globalInterceptorConfigurer(GrpcExceptionInterceptor interceptor) {
        return serverBuilder -> serverBuilder.intercept(interceptor);
    }
}
