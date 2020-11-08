package com.example.quiz.dto;

import com.example.quiz.model.Category;
import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class QuestionDto {
    private Long id;
    private String questionText;
    private Category category;
    private String imagePath;
    private List<AnswerDto> answers;

    public QuestionDto() {
    }

    public QuestionDto(String questionText, Category category, String imagePath, List<AnswerDto> answers) {
        this.questionText = questionText;
        this.category = category;
        this.imagePath = imagePath;
        this.answers = answers;
    }
}
