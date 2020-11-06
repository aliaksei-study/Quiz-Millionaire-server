package com.example.quiz.dto;

import com.example.quiz.model.enumeration.Difficulty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StatisticsDto {
    private Long id;
    private Difficulty highDifficulty;
    private int numberOfGames;
    private int score;
    private PlayerDto playerDto;

    public StatisticsDto(int score) {
        this.score = score;
    }
}
