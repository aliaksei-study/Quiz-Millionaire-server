package com.example.quiz.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AnswerStatisticsDto {
    private Long id;
    private QuestionDto questionDto;
    private AnswerDto answerDto;
    private PlayerDto playerDto;
}
