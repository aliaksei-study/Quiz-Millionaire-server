package com.example.quiz.service;

import com.example.quiz.dto.StatisticsDto;
import com.example.quiz.exception.PlayerNotFoundException;
import com.example.quiz.mapper.Mapper;
import com.example.quiz.model.Player;
import com.example.quiz.model.Statistics;
import com.example.quiz.model.enumeration.Difficulty;
import com.example.quiz.repository.PlayerRepository;
import com.example.quiz.repository.StatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StatisticsServiceImpl implements IStatisticsService {
    private StatisticsRepository statisticsRepository;
    private IAnswerStatistics answerStatisticsService;
    private PlayerRepository playerRepository;

    @Autowired
    public StatisticsServiceImpl(StatisticsRepository statisticsRepository, IAnswerStatistics answerStatistics,
                                 PlayerRepository playerRepository) {
        this.statisticsRepository = statisticsRepository;
        this.answerStatisticsService = answerStatistics;
        this.playerRepository = playerRepository;
    }

    @Override
    public StatisticsDto saveStatistics(StatisticsDto statisticsDto, Player player) {
        Optional<Statistics> statisticsOptional;
        statisticsOptional = statisticsRepository.findStatisticsByPlayer(player);
        statisticsDto.setHighDifficulty(defineDifficultyByNumberOfAnsweredQuestions(statisticsDto.getScore()));
        if(statisticsOptional.isEmpty()) {
            Statistics statistics = Mapper.map(statisticsDto, Statistics.class);
            statistics.setPlayer(player);
            statistics.setNumberOfGames(1);
            statistics = statisticsRepository.save(statistics);
            return Mapper.map(statistics, StatisticsDto.class);
        } else {
            return updateStatistics(statisticsOptional.get(), statisticsDto);
        }
    }

    @Override
    public List<StatisticsDto> getStatistics() {
        return Mapper.mapAll(statisticsRepository.findAll(), StatisticsDto.class);
    }

    @Override
    public void deletePlayerStatistics(List<Long> playerStatisticsIds) {
        for(Long statisticId: playerStatisticsIds) {
            Optional<Statistics> statisticsOptional = statisticsRepository.findById(statisticId);
            statisticsOptional.ifPresent(statistics -> {
                deleteLikesAndDislikes(statistics.getPlayer());
                answerStatisticsService.deleteAnswerStatisticsByPlayer(statistics.getPlayer());
            });
            statisticsRepository.deleteById(statisticId);
        }
    }

    private StatisticsDto updateStatistics(Statistics statistics, StatisticsDto statisticsDto) {
        if(statistics.getHighDifficulty().getCost() < statisticsDto.getHighDifficulty().getCost()) {
            statistics.setHighDifficulty(statisticsDto.getHighDifficulty());
        }
        if(statistics.getScore() < statisticsDto.getScore()) {
            statistics.setScore(statisticsDto.getScore());
        }
        statistics.setNumberOfGames(statistics.getNumberOfGames() + 1);
        statistics = statisticsRepository.save(statistics);
        return Mapper.map(statistics, StatisticsDto.class);
    }

    private void deleteLikesAndDislikes(Player player) {
        player.getLikedQuestions().clear();
        player.getDislikedQuestions().clear();
        playerRepository.save(player);
    }

    private Difficulty defineDifficultyByNumberOfAnsweredQuestions(int score) {
        if(score == 14 || score == 15) {
            return Difficulty.NIGHTMARE;
        } else if(score == 12 || score == 13) {
            return Difficulty.HARD;
        } else if(score >= 6) {
            return Difficulty.MEDIUM;
        } else {
            return Difficulty.EASY;
        }
    }
}
