package com.ticket.userservice.user.infrastructre.repository;

import com.ticket.userservice.user.domain.entity.User;
import com.ticket.userservice.user.domain.model.UserCredential;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringMongoUserRepository extends MongoRepository<User, UUID> {

    boolean existsByEmail(String email);

    @Query(value = "{ 'email': ?0 }", fields = "{ 'email': 1, 'password': 1}")
    Optional<UserCredential> findUserCredentialByEmail(String email);
}
