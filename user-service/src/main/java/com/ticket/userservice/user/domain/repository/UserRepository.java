package com.ticket.userservice.user.domain.repository;

import com.ticket.userservice.user.domain.entity.User;
import com.ticket.userservice.user.domain.model.UserCredential;

import java.util.Optional;

public interface UserRepository {

    void save(User user);

    boolean existByEmail(String email);

    Optional<UserCredential> findByEmail(String email);
}
