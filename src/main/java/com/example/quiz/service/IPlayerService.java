package com.example.quiz.service;

import com.example.quiz.dto.PlayerDto;
import com.example.quiz.exception.PlayerNotFoundException;
import com.example.quiz.exception.QuestionAlreadyDislikedException;
import com.example.quiz.exception.QuestionAlreadyLikedException;
import com.example.quiz.exception.QuestionNotFoundException;
import com.example.quiz.model.Player;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IPlayerService extends UserDetailsService {
    boolean isPlayerWithSuchUsernameExists(String username);
    void registerPlayer(String email, String password);
    List<PlayerDto> getAllPlayers();
    PlayerDto likeQuestion(Long questionId, Player player) throws QuestionNotFoundException, QuestionAlreadyLikedException;
    PlayerDto dislikeQuestion(Long questionId, Player player) throws QuestionNotFoundException, QuestionAlreadyDislikedException;
    void deletePlayers(List<Long> playerIds) throws PlayerNotFoundException;
}
