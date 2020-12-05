package com.example.quiz.controller.v1.response;

import com.example.quiz.dto.AnswerDto;
import com.example.quiz.model.Category;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Setter
public class PlayerQuestionResponse {
    private Long id;
    private String questionText;
    private Category category;
    private String imagePath;
    private List<AnswerDto> answers;

    public PlayerQuestionResponse() {
    }

    public PlayerQuestionResponse(String questionText, Category category, String imagePath, List<AnswerDto> answers) {
        this.questionText = questionText;
        this.category = category;
        this.imagePath = imagePath;
        this.answers = answers;
    }
}
