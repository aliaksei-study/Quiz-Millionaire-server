package com.example.quiz.dto;

import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
public class QuestionDto {
    private Long id;
    private String questionText;
    private String imagePath;
    private List<AnswerDto> answers;

    public QuestionDto() {
    }

    public QuestionDto(Long id, String questionText, String imagePath, List<AnswerDto> answers) {
        this.id = id;
        this.questionText = questionText;
        this.imagePath = imagePath;
        this.answers = answers;
    }
}
