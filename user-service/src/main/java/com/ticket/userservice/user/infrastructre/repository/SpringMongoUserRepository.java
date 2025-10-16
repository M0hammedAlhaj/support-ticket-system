package com.ticket.userservice.user.infrastructre.repository;

import com.ticket.userservice.user.domain.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringMongoUserRepository extends MongoRepository<User, UUID> {
}
