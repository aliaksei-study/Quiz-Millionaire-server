package com.example.quiz.model.enumeration;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

public enum Role implements Serializable, GrantedAuthority {
    PLAYER, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
