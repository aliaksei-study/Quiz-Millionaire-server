package com.example.quiz.repository;

import com.example.quiz.model.Answer;
import com.example.quiz.model.AnswerStatistics;
import com.example.quiz.model.Player;
import com.example.quiz.model.Question;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AnswerStatisticsRepository extends CrudRepository<AnswerStatistics, Long> {
    Optional<AnswerStatistics> findAnswerStatisticsByAnswerAndQuestionAndPlayer(Answer answer, Question question,
                                                                                   Player player);
}
