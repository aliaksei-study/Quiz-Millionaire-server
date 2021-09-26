package com.example.quiz.service;

import com.example.quiz.controller.v1.response.TranslatedQuestionResponse;
import com.example.quiz.dto.QuestionDto;
import com.example.quiz.dto.SatisfiedQuestionStatisticsDto;
import com.example.quiz.dto.TranslatedQuestionDto;
import com.example.quiz.exception.CategoryNotFoundException;
import com.example.quiz.exception.QuestionNotFoundException;
import com.example.quiz.model.Question;

import java.util.List;

public interface IQuestionService {
    TranslatedQuestionResponse updateQuestion(TranslatedQuestionDto questionDto, Long id) throws QuestionNotFoundException, CategoryNotFoundException;
    void deleteQuestion(Long id) throws QuestionNotFoundException;
    Question getQuestionById(Long questionId) throws QuestionNotFoundException;
    List<SatisfiedQuestionStatisticsDto> getSatisfiedQuestionStatistics();
    List<TranslatedQuestionResponse> getQuestions();
    TranslatedQuestionResponse saveQuestion(TranslatedQuestionDto questionDto) throws CategoryNotFoundException;
    List<QuestionDto> getFifteenRandomQuestions(String Language);
    void processPlayerQuestions();
}
