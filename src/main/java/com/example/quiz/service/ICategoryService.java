package com.example.quiz.service;

import com.example.quiz.dto.CategoryDto;

import java.util.List;

public interface ICategoryService {
    List<CategoryDto> getCategories();
    CategoryDto saveCategory(CategoryDto categoryDto);
}
