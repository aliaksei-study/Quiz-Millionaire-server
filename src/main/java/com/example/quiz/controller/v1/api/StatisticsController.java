package com.example.quiz.controller.v1.api;

import com.example.quiz.controller.v1.request.StatisticsRequest;
import com.example.quiz.dto.StatisticsDto;
import com.example.quiz.mapper.Mapper;
import com.example.quiz.model.Player;
import com.example.quiz.service.IStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "/api/v1/statistics")
public class StatisticsController {
    private IStatisticsService statisticsService;

    @Autowired
    public StatisticsController(IStatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StatisticsDto> saveStatistics(@RequestBody @Valid StatisticsRequest statisticsRequest,
                                                        @AuthenticationPrincipal Player player)
            throws URISyntaxException {
        StatisticsDto statisticsDto;
        statisticsDto = statisticsService.saveStatistics(Mapper.map(statisticsRequest, StatisticsDto.class), player);
        return ResponseEntity.created(new URI("/api/v1/statistics/" + statisticsDto.getId()))
                .body(statisticsDto);
    }
}
