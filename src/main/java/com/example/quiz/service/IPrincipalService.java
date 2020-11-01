package com.example.quiz.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface IPrincipalService extends UserDetailsService {
    boolean isPrincipalWithSuchUsernameExists(String username);
    void registerPrincipal(String email, String password);
}
