package com.example.quiz.dto;

import com.example.quiz.model.enumeration.Difficulty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SatisfiedQuestionStatisticsDto {
    private String questionText;
    private Difficulty difficulty;
    private CategoryDto category;
    private int numberOfLikes;
    private int numberOfDislikes;
}
