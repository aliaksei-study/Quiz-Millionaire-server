package com.example.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TranslatedCategoryDto {
    private Long id;
    private List<TranslatedTextDto> categoryNameTranslates;
    private String categoryTag;
}
