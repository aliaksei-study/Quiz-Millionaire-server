package com.example.quiz.service;

import com.example.quiz.dto.CategoryDto;
import com.example.quiz.exception.CategoryNotFoundException;
import com.example.quiz.mapper.Mapper;
import com.example.quiz.model.Category;
import com.example.quiz.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements ICategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDto> getCategories() {
        return Mapper.mapAll(categoryRepository.findAll(), CategoryDto.class);
    }

    @Override
    public CategoryDto saveCategory(CategoryDto categoryDto) {
        Category savedCategory = categoryRepository.save(Mapper.map(categoryDto, Category.class));
        return Mapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public Category getCategoryById(Long id) throws CategoryNotFoundException {
        return categoryRepository.findCategoryById(id).orElseThrow(() ->
                new CategoryNotFoundException("Category with id: " + id + " not found."));
    }
}
