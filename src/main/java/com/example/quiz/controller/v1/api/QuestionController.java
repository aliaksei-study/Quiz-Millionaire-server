package com.example.quiz.controller.v1.api;

import com.example.quiz.controller.v1.request.AddingQuestionRequest;
import com.example.quiz.dto.AnswerDto;
import com.example.quiz.dto.QuestionDto;
import com.example.quiz.service.IQuestionService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
@RequestMapping("/api/v1/questions")
public class QuestionController {
    private final IQuestionService questionService;

    public QuestionController(IQuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping(value = "/random-questions")
    public List<QuestionDto> getQuestions() {
        List<QuestionDto> randomQuestions = questionService.getFifteenRandomQuestions();
        randomQuestions.forEach(questionDto -> Collections.shuffle(questionDto.getAnswers()));
        return randomQuestions;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QuestionDto> saveQuestion(@RequestBody @Valid AddingQuestionRequest request)
            throws URISyntaxException {
        QuestionDto questionDto = new QuestionDto(request.getQuestionText(), null,
                request.getQuestionImageUrl(), request.getAnswers());
        questionDto = questionService.saveQuestion(questionDto);
        return ResponseEntity.created(new URI("/api/v1/questions/" + questionDto.getId()))
                .body(questionDto);
    }
}
