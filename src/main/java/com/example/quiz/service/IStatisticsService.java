package com.example.quiz.service;

import com.example.quiz.dto.StatisticsDto;
import com.example.quiz.exception.PlayerNotFoundException;
import com.example.quiz.model.Player;
import com.example.quiz.model.Statistics;

import java.util.List;

public interface IStatisticsService {
    StatisticsDto saveStatistics(StatisticsDto statisticsDto, Player player);
    List<StatisticsDto> getStatistics();
    void deletePlayerStatistics(List<Long> playerStatisticsIds);
}
