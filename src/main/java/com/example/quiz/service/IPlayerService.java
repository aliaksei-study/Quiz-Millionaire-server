package com.example.quiz.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface IPlayerService extends UserDetailsService {
    boolean isPlayerWithSuchUsernameExists(String username);
    void registerPlayer(String email, String password);
}
