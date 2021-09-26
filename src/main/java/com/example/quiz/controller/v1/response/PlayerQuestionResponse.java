package com.example.quiz.controller.v1.response;

import com.example.quiz.dto.AnswerDto;
import com.example.quiz.dto.CategoryDto;
import com.example.quiz.model.Category;
import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerQuestionResponse {
    private Long id;
    private String questionText;
    private CategoryDto category;
    private String imagePath;
    private List<AnswerDto> answers;
}
