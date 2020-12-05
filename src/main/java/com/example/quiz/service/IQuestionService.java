package com.example.quiz.service;

import com.example.quiz.dto.QuestionDto;
import com.example.quiz.exception.QuestionNotFoundException;
import com.example.quiz.model.Question;

import java.util.List;

public interface IQuestionService {
    QuestionDto updateQuestion(QuestionDto questionDto, Long id) throws QuestionNotFoundException;
    void deleteQuestion(Long id);
    Question getQuestionById(Long questionId) throws QuestionNotFoundException;
    List<QuestionDto> getQuestions();
    QuestionDto saveQuestion(QuestionDto questionDto);
    List<QuestionDto> getFifteenRandomQuestions();
    void processPlayerQuestions();
}
