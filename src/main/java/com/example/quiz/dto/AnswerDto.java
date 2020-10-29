package com.example.quiz.dto;

import lombok.*;

@ToString
@Getter
@Setter
public class AnswerDto {
    private Long id;
    private String answerText;
    private Boolean isCorrect;

    public AnswerDto() {
    }

    public AnswerDto(Long id, String answerText, Boolean isCorrect) {
        this.id = id;
        this.answerText = answerText;
        this.isCorrect = isCorrect;
    }
}
