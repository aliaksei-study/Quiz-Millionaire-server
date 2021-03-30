package com.example.quiz.dto;

import com.example.quiz.model.enumeration.Difficulty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SatisfiedQuestionStatisticsDto {
    private List<TranslatedTextDto> questionTextTranslates;
    private Difficulty difficulty;
    private Long categoryId;
    private int numberOfLikes;
    private int numberOfDislikes;
}
