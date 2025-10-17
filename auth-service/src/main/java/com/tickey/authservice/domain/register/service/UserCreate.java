package com.tickey.authservice.domain.register.service;

import com.tickey.authservice.domain.register.model.UserAuth;

public interface UserCreate {
    String createUser(UserAuth userAuth);
}
