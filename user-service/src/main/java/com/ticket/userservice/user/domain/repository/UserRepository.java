package com.ticket.userservice.user.domain.repository;

import com.ticket.userservice.user.domain.entity.User;

import java.util.Optional;

public interface UserRepository {

    void save(User user);

    Optional<User> findByEmail(String email);
}
