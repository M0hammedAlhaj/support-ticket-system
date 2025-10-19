package com.tickey.authservice.domain.service;

import com.tickey.authservice.domain.model.NewUser;

public interface UserCreate {
    String createUser(NewUser newUser);
}
