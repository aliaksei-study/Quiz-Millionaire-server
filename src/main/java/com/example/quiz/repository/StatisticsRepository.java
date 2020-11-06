package com.example.quiz.repository;

import com.example.quiz.model.Player;
import com.example.quiz.model.Statistics;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface StatisticsRepository extends CrudRepository<Statistics, Long> {
    Optional<Statistics> findStatisticsByPlayer(Player player);
    List<Statistics> findAll();
}
