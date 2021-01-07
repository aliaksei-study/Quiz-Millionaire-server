package com.example.quiz.service;

import com.example.quiz.dto.AnswerStatisticsDto;
import com.example.quiz.dto.QuestionAnswersStatisticsDto;
import com.example.quiz.exception.AnswerNotFoundException;
import com.example.quiz.exception.QuestionNotFoundException;
import com.example.quiz.model.Player;

import java.util.List;
import java.util.Map;

public interface IAnswerStatistics {
    AnswerStatisticsDto savePlayerQuestionAnswer(Player player, Long questionId, Long answerId)
            throws QuestionNotFoundException, AnswerNotFoundException;
    Map<Long, Integer> getPlayerAnswersHistogram(Long questionId) throws QuestionNotFoundException;
    List<QuestionAnswersStatisticsDto> getQuestionAnswersHistograms();
    void deleteAnswerStatisticsByPlayer(Player player);
}
