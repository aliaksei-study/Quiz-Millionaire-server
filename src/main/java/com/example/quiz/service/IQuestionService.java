package com.example.quiz.service;

import com.example.quiz.model.Question;

import java.util.List;
import java.util.Set;

public interface IQuestionService {
    void addQuestion(Question question);
    void updateQuestion(Question question, Long id);
    void deleteQuestion(Long id);
    Set<Question> getQuestions();
}
