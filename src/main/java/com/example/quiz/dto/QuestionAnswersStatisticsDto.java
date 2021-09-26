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
    private List<TranslatedTextDto> questionTextTranslates;
    private Difficulty difficulty;
    private Long categoryId;
    private List<AnswerHistogramDto> answerHistograms;
}
