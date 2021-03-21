package com.example.quiz.service;

import com.example.quiz.dto.CategoryDto;
import com.example.quiz.dto.TranslatedCategoryDto;
import com.example.quiz.exception.CategoryNotFoundException;
import com.example.quiz.model.Category;

import java.util.List;

public interface ICategoryService {
    List<TranslatedCategoryDto> getCategories();
    TranslatedCategoryDto saveCategory(TranslatedCategoryDto categoryDto);
    Category getCategoryById(Long id) throws CategoryNotFoundException;
}
