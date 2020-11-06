package com.example.quiz.service;

import com.example.quiz.dto.PlayerDto;
import com.example.quiz.model.Player;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.TreeSet;

public interface IPlayerService extends UserDetailsService {
    boolean isPlayerWithSuchUsernameExists(String username);
    void registerPlayer(String email, String password);
    List<PlayerDto> getAllPlayers();
}
