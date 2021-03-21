package com.example.quiz.service;

import com.example.quiz.dto.TranslatedCategoryDto;
import com.example.quiz.exception.CategoryNotFoundException;
import com.example.quiz.mapper.Mapper;
import com.example.quiz.model.Category;
import com.example.quiz.model.LocalizedCategory;
import com.example.quiz.repository.CategoryRepository;
import com.example.quiz.repository.LocalizedCategoryRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements ICategoryService {
    private final CategoryRepository categoryRepository;
    private final ILanguageService languageService;
    private final LocalizedCategoryRepository localizedCategoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               ILanguageService languageService,
                               LocalizedCategoryRepository localizedCategoryRepository) {
        this.categoryRepository = categoryRepository;
        this.languageService = languageService;
        this.localizedCategoryRepository = localizedCategoryRepository;
    }

    @Override
    public List<TranslatedCategoryDto> getCategories() {
        return Mapper.mapAll(categoryRepository.findAll(), TranslatedCategoryDto.class);
    }

    @Override
    public TranslatedCategoryDto saveCategory(TranslatedCategoryDto categoryDto) {
        categoryDto.getCategoryNameTranslates().forEach(languageService::setPersistedLanguageIfNotExist);
        Category category = Mapper.map(categoryDto, Category.class);

        category.setCategoryNameTranslates(category.getCategoryNameTranslates()
                .stream()
                .map(this::persistCategoryTranslateIfNotExist)
                .collect(Collectors.toList()));

        category = categoryRepository.save(category);
        return Mapper.map(category, TranslatedCategoryDto.class);
    }

    @Override
    public Category getCategoryById(Long id) throws CategoryNotFoundException {
        return categoryRepository.findCategoryById(id).orElseThrow(() ->
                new CategoryNotFoundException("Category with id: " + id + " not found."));
    }

    public LocalizedCategory persistCategoryTranslateIfNotExist(LocalizedCategory localizedCategory) {
        if (localizedCategory.getId() == null) {
            return localizedCategoryRepository.save(localizedCategory);
        }
        return localizedCategory;
    }
}
