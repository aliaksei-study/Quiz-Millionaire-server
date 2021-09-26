package com.example.quiz.controller.v1.response;

import com.example.quiz.dto.*;
import com.example.quiz.model.enumeration.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TranslatedQuestionResponse {
    private Long id;
    private List<TranslatedTextDto> questionTextTranslates;
    private String imagePath;
    private Boolean isTemporal;
    private Difficulty difficulty;
    private TranslatedCategoryDto category;
    private List<TranslatedAnswerDto> answers;
    private String createdDate;
    private String lastModifiedDate;
    private String createdBy;
}
