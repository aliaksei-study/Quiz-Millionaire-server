package com.example.quiz.repository;

import com.example.quiz.model.Player;
import com.example.quiz.model.Question;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends CrudRepository<Player, Long> {
    Optional<Player> findPlayerByUsername(String username);
    List<Player> findAll();
    List<Player> findPlayerByLikedQuestions(Question question);
    List<Player> findPlayersByDislikedQuestions(Question question);
}
