package com.tickey.authservice.domain.service;

import com.tickey.authservice.domain.model.UserCredential;

public interface UserCredentialPort {

    UserCredential findUserCredential(String email);
}
