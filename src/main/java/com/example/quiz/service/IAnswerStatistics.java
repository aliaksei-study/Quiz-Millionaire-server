package com.example.quiz.service;

import com.example.quiz.dto.AnswerStatisticsDto;
import com.example.quiz.exception.AnswerNotFoundException;
import com.example.quiz.exception.QuestionNotFoundException;
import com.example.quiz.model.Player;

public interface IAnswerStatistics {
    AnswerStatisticsDto savePlayerQuestionAnswer(Player player, Long questionId, Long answerId)
            throws QuestionNotFoundException, AnswerNotFoundException;
}
