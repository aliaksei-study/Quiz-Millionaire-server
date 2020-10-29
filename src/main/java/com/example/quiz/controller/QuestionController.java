package com.example.quiz.controller;

import com.example.quiz.dto.QuestionDto;
import com.example.quiz.service.IQuestionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    private final IQuestionService questionService;

    public QuestionController(IQuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public List<QuestionDto> getQuestions() {
        return questionService.getQuestions();
    }
}
