package com.example.quiz.controller.v1.request;

import com.example.quiz.dto.CategoryDto;
import com.example.quiz.model.enumeration.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditQuestionRequest {
    private CategoryDto category;
    private Difficulty difficulty;
    private Boolean isTemporal;
}
