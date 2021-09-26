package com.example.quiz.service;

import com.example.quiz.dto.CategoryDto;
import com.example.quiz.dto.TranslatedCategoryDto;
import com.example.quiz.exception.CategoryNotFoundException;
import com.example.quiz.model.Category;

import java.util.List;

public interface ICategoryService {
    List<TranslatedCategoryDto> getCategories();

    TranslatedCategoryDto saveCategory(TranslatedCategoryDto categoryDto);

    TranslatedCategoryDto updateCategory(TranslatedCategoryDto updatedCategory, Long id)
            throws CategoryNotFoundException;

    void deleteCategory(Long id) throws CategoryNotFoundException;

    Category getCategoryById(Long id) throws CategoryNotFoundException;
}
