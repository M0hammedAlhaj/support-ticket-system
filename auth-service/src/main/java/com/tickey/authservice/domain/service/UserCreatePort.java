package com.tickey.authservice.domain.service;

import com.tickey.authservice.domain.model.NewUser;

public interface UserCreatePort {
    String createUser(NewUser newUser);
}
