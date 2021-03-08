package com.example.quiz.service;

import com.example.quiz.dto.CategoryDto;
import com.example.quiz.exception.CategoryNotFoundException;
import com.example.quiz.model.Category;

import java.util.List;

public interface ICategoryService {
    List<CategoryDto> getCategories();
    CategoryDto saveCategory(CategoryDto categoryDto);
    Category getCategoryById(Long id) throws CategoryNotFoundException;
}
