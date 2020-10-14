package com.example.quiz.controller;

import com.example.quiz.model.Question;
import com.example.quiz.repository.AnswerRepository;
import com.example.quiz.repository.QuestionRepository;
import com.example.quiz.service.IQuestionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    private final IQuestionService questionService;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public QuestionController(IQuestionService questionService, QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionService = questionService;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    @GetMapping
    public Set<Question> getQuestions() {
        return questionService.getQuestions();
    }
}
