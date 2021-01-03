package com.example.quiz.controller.v1.api;

import com.example.quiz.controller.v1.response.PlayerQuestionResponse;
import com.example.quiz.controller.v1.request.AddingQuestionRequest;
import com.example.quiz.controller.v1.request.EditQuestionRequest;
import com.example.quiz.dto.QuestionDto;
import com.example.quiz.dto.SatisfiedQuestionStatisticsDto;
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
//@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
@RequestMapping("/api/v1/questions")
public class QuestionController {
    private final IQuestionService questionService;

    public QuestionController(IQuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping(value = "/random-questions")
    public List<PlayerQuestionResponse> getRandomQuestions() {
        List<PlayerQuestionResponse> randomQuestions = Mapper.mapAll(questionService.getFifteenRandomQuestions(), PlayerQuestionResponse.class);
        randomQuestions.forEach(questionDto -> Collections.shuffle(questionDto.getAnswers()));
        return randomQuestions;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<QuestionDto>> getQuestions() {
        List<QuestionDto> questions = questionService.getQuestions();
        return ResponseEntity.ok(questions);
    }

    @GetMapping(value = "/satisfied-statistics", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SatisfiedQuestionStatisticsDto>> getSatisfiedQuestionStatistics() {
        return ResponseEntity.ok(questionService.getSatisfiedQuestionStatistics());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QuestionDto> saveQuestion(@RequestBody @Valid AddingQuestionRequest request)
            throws URISyntaxException {
        QuestionDto questionDto = new QuestionDto(null, request.getQuestionText(), request.getQuestionImageUrl(),
                request.getIsTemporal(), request.getDifficulty(), request.getCategory(), request.getAnswers(),
                null, null, null);
        questionDto = questionService.saveQuestion(questionDto);
        return ResponseEntity.created(new URI("/api/v1/questions/" + questionDto.getId()))
                .body(questionDto);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QuestionDto> updateQuestion(@PathVariable("id") Long id,
                                                      @RequestBody @Valid EditQuestionRequest editQuestionRequest)
            throws QuestionNotFoundException {
        QuestionDto questionDto = questionService.updateQuestion(Mapper.map(editQuestionRequest, QuestionDto.class), id);
        return ResponseEntity.accepted().body(questionDto);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteQuestion(@PathVariable("id") Long id) throws QuestionNotFoundException {
        questionService.deleteQuestion(id);
    }
}
