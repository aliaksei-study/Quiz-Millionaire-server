package com.example.quiz.service;

import com.example.quiz.dto.StatisticsDto;
import com.example.quiz.model.Player;
import com.example.quiz.model.Statistics;

public interface IStatisticsService {
    StatisticsDto saveStatistics(StatisticsDto statisticsDto, Player player);
}
