package com.example.quiz.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AnswerHistogramDto {
    private TranslatedAnswerDto answer;
    private int numberOfAnswers;
}
