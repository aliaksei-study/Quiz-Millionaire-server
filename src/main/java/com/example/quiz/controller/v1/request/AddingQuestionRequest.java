package com.example.quiz.controller.v1.request;

import com.example.quiz.dto.AnswerDto;
import com.example.quiz.model.Category;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddingQuestionRequest {
    private String questionImageUrl;
    private Category category;
    private String questionText;
    private List<AnswerDto> answers;
}
