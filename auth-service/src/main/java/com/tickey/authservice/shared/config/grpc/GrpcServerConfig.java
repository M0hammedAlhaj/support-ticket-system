package com.tickey.authservice.shared.config.grpc;

import com.tickey.authservice.shared.handler.GrpcHandlerException;
import net.devh.boot.grpc.server.serverfactory.GrpcServerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcServerConfig {

    @Bean
    public GrpcServerConfigurer globalInterceptorConfigurer(GrpcHandlerException interceptor) {
        return serverBuilder -> serverBuilder.intercept(interceptor);
    }
}
