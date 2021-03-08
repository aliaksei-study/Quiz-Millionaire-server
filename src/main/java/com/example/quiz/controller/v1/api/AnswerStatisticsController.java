package com.example.quiz.controller.v1.api;

import com.example.quiz.controller.v1.request.PlayerAnswerRequest;
import com.example.quiz.dto.AnswerStatisticsDto;
import com.example.quiz.dto.QuestionAnswersStatisticsDto;
import com.example.quiz.exception.AnswerNotFoundException;
import com.example.quiz.exception.QuestionNotFoundException;
import com.example.quiz.model.Player;
import com.example.quiz.service.IAnswerStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/answer-statistics")
public class AnswerStatisticsController {

    private IAnswerStatistics answerStatistics;

    @Autowired
    public AnswerStatisticsController(IAnswerStatistics answerStatistics) {
        this.answerStatistics = answerStatistics;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AnswerStatisticsDto> savePlayerAnswer(
            @RequestBody @Valid PlayerAnswerRequest playerAnswerRequest, @AuthenticationPrincipal Player player)
            throws AnswerNotFoundException, QuestionNotFoundException, URISyntaxException {
        AnswerStatisticsDto answerStatisticsDto;
        answerStatisticsDto = answerStatistics.savePlayerQuestionAnswer(player, playerAnswerRequest.getQuestionId(),
                playerAnswerRequest.getAnswerId());
        return ResponseEntity.created(new URI("/api/v1/answer-statistics/" + answerStatisticsDto.getId()))
                .body(answerStatisticsDto);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<Long, Integer>> getQuestionStatistics(@PathVariable("id") Long id)
            throws QuestionNotFoundException {
        return ResponseEntity.ok(answerStatistics.getPlayerAnswersHistogram(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<QuestionAnswersStatisticsDto>> getQuestionAnswersHistograms() {
        return ResponseEntity.ok(answerStatistics.getQuestionAnswersHistograms());
    }

}
