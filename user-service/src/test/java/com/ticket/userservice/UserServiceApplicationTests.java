package com.ticket.userservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {
        "spring.data.mongodb.username=",
        "spring.data.mongodb.password=",
        "spring.data.mongodb.authentication-database=",
        "spring.data.mongodb.host=",
        "spring.data.mongodb.port="
    }
)
@Testcontainers
@ActiveProfiles("test")
class UserServiceApplicationTests {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0");

    @Container
    static RabbitMQContainer rabbitMQContainer = new RabbitMQContainer("rabbitmq:3-management-alpine");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        // MongoDB configuration - use getReplicaSetUrl() as recommended by Testcontainers
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
        registry.add("spring.data.mongodb.database", () -> "testdb");
        
        // RabbitMQ configuration
        registry.add("spring.rabbitmq.host", rabbitMQContainer::getHost);
        registry.add("spring.rabbitmq.port", () -> rabbitMQContainer.getMappedPort(5672));
    }

    @Test
    void contextLoads() {
        // Test that Spring context loads successfully with Testcontainers
    }

}
