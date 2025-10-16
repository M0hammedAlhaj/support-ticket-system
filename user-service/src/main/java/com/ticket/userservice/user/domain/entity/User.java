package com.ticket.userservice.user.domain.entity;

import com.ticket.userservice.user.domain.model.UserType;

import java.time.LocalDateTime;
import java.util.UUID;

public class User {

    private UUID id;

    private String email;

    private String password;

    private UserType userType;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}