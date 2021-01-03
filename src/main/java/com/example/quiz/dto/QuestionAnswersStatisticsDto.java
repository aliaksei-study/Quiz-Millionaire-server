package com.example.quiz.dto;

import com.example.quiz.model.enumeration.Difficulty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QuestionAnswersStatisticsDto {
    private String questionText;
    private Difficulty difficulty;
    private CategoryDto category;
    private List<AnswerHistogramDto> answerHistograms;
}
