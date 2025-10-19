package com.ticket.userservice.user.domain.model;

import lombok.Getter;

@Getter
public enum UserType {
    CUSTOMER(1),
    AGENT(2);

    private final int code;

    UserType(int code) {
        this.code = code;
    }

    public static UserType fromString(String userType) {
        for (UserType type : UserType.values()) {
            if (type.name().equalsIgnoreCase(userType)) {
                return type;
            }
        }
        throw new IllegalArgumentException("UserType not found: " + userType);
    }

    public static UserType fromCode(int code) {
        for (UserType type : UserType.values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("UserType code not found: " + code);
    }
}
