package com.example.quiz.dto;


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
public class TranslatedQuestionDto {
    private Long category;
    private Difficulty difficulty;
    private Boolean isTemporal;
    private String imagePath;
    private List<TranslatedTextDto> questionTextTranslates;
    private List<TranslatedAnswerDto> answers;
}
