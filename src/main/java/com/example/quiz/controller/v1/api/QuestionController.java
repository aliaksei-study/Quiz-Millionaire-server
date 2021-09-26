package com.example.quiz.controller.v1.api;

import com.example.quiz.controller.v1.response.PlayerQuestionResponse;
import com.example.quiz.controller.v1.response.TranslatedQuestionResponse;
import com.example.quiz.dto.SatisfiedQuestionStatisticsDto;
import com.example.quiz.dto.TranslatedQuestionDto;
import com.example.quiz.exception.CategoryNotFoundException;
import com.example.quiz.exception.QuestionNotFoundException;
import com.example.quiz.mapper.Mapper;
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
@RequestMapping("/api/v1/questions")
public class QuestionController {
    private final IQuestionService questionService;

    public QuestionController(IQuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping(value = "/random-questions")
    public List<PlayerQuestionResponse> getRandomQuestions(@RequestHeader("accept-language") String language) {
        List<PlayerQuestionResponse> randomQuestions = Mapper.mapAll(questionService.getFifteenRandomQuestions(language), PlayerQuestionResponse.class);
        randomQuestions.forEach(questionDto -> Collections.shuffle(questionDto.getAnswers()));
        return randomQuestions;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TranslatedQuestionResponse>> getQuestions() {
        List<TranslatedQuestionResponse> questions = questionService.getQuestions();
        return ResponseEntity.ok(questions);
    }

    @GetMapping(value = "/satisfied-statistics", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SatisfiedQuestionStatisticsDto>> getSatisfiedQuestionStatistics() {
        return ResponseEntity.ok(questionService.getSatisfiedQuestionStatistics());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TranslatedQuestionResponse> saveQuestion(@RequestBody TranslatedQuestionDto translatedQuestionDto)
            throws URISyntaxException, CategoryNotFoundException {
        TranslatedQuestionResponse questionDto = questionService.saveQuestion(translatedQuestionDto);
        return ResponseEntity.created(new URI("/api/v1/questions/" + questionDto.getId()))
                .body(questionDto);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TranslatedQuestionResponse> updateQuestion(@PathVariable("id") Long id,
                                                      @RequestBody @Valid TranslatedQuestionDto translatedQuestionDto)
            throws QuestionNotFoundException, CategoryNotFoundException {
        TranslatedQuestionResponse questionDto = questionService.updateQuestion(translatedQuestionDto, id);
        return ResponseEntity.accepted().body(questionDto);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteQuestion(@PathVariable("id") Long id) throws QuestionNotFoundException {
        questionService.deleteQuestion(id);
    }
}
