package com.example.quiz.controller.v1.request;

import com.example.quiz.model.enumeration.Difficulty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StatisticsRequest {
    private Difficulty highDifficulty;
    private int score;
}
