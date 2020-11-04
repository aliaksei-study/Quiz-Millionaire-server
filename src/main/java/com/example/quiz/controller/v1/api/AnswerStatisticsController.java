package com.example.quiz.controller.v1.api;

import com.example.quiz.controller.v1.request.PlayerAnswerRequest;
import com.example.quiz.dto.AnswerStatisticsDto;
import com.example.quiz.exception.AnswerNotFoundException;
import com.example.quiz.exception.QuestionNotFoundException;
import com.example.quiz.model.Player;
import com.example.quiz.service.IAnswerStatistics;
import org.apache.tomcat.util.http.HeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/v1/answer-statistics")
public class AnswerStatisticsController {

    private IAnswerStatistics answerStatistics;

    @Autowired
    public AnswerStatisticsController(IAnswerStatistics answerStatistics) {
        this.answerStatistics = answerStatistics;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AnswerStatisticsDto> savePrincipalAnswer(
            @RequestBody @Valid PlayerAnswerRequest playerAnswerRequest, @AuthenticationPrincipal Player player)
            throws AnswerNotFoundException, QuestionNotFoundException, URISyntaxException {
        AnswerStatisticsDto answerStatisticsDto;
        answerStatisticsDto = answerStatistics.savePlayerQuestionAnswer(player, playerAnswerRequest.getQuestionId(),
                playerAnswerRequest.getAnswerId());
        return ResponseEntity.created(new URI("/api/v1/answer-statistics/" + answerStatisticsDto.getId()))
                .body(answerStatisticsDto);
    }

}
