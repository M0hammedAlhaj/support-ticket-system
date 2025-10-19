package com.ticket.userservice.user.domain.repository;

import com.ticket.userservice.user.domain.entity.User;

public interface UserRepository {

    void save(User user);

    boolean existByEmail(String email);
}
