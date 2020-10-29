package com.example.quiz.service;

import com.example.quiz.dto.QuestionDto;
import com.example.quiz.model.Question;

import java.util.List;

public interface IQuestionService {
    void addQuestion(QuestionDto questionDto);
    void updateQuestion(QuestionDto questionDto, Long id);
    void deleteQuestion(Long id);
    List<QuestionDto> getQuestions();
}
