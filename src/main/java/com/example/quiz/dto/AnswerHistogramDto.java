package com.example.quiz.dto;

import com.example.quiz.model.Answer;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AnswerHistogramDto {
    private Answer answer;
    private int numberOfAnswers;
}
