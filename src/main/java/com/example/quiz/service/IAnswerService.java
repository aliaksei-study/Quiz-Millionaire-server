package com.example.quiz.service;

import com.example.quiz.model.Answer;

import java.util.List;
import java.util.Set;

public interface IAnswerService {
    void addAnswer(Answer answer);
    void updateAnswer(Answer newAnswer, Long id);
    void deleteAnswer(Long id);
    List<Answer> getAnswers();
}
