package com.example.quiz.controller.v1.request;

import com.example.quiz.dto.AnswerDto;
import com.example.quiz.dto.CategoryDto;
import com.example.quiz.model.enumeration.Difficulty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddingQuestionRequest {
    private CategoryDto category;
    private Difficulty difficulty;
    private Boolean isTemporal;
    private String questionImageUrl;
    private String questionText;
    private List<AnswerDto> answers;
}
