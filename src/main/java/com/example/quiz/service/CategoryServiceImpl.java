package com.example.quiz.service;

import com.example.quiz.dto.TranslatedCategoryDto;
import com.example.quiz.exception.CategoryNotFoundException;
import com.example.quiz.exception.QuestionNotFoundException;
import com.example.quiz.mapper.Mapper;
import com.example.quiz.model.Category;
import com.example.quiz.model.LocalizedCategory;
import com.example.quiz.model.Question;
import com.example.quiz.repository.CategoryRepository;
import com.example.quiz.repository.LocalizedCategoryRepository;
import com.example.quiz.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements ICategoryService {
    private final CategoryRepository categoryRepository;
    private final QuestionRepository questionRepository;
    private final ILanguageService languageService;
    private final LocalizedCategoryRepository localizedCategoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               ILanguageService languageService,
                               LocalizedCategoryRepository localizedCategoryRepository,
                               QuestionRepository questionRepository) {
        this.categoryRepository = categoryRepository;
        this.languageService = languageService;
        this.localizedCategoryRepository = localizedCategoryRepository;
        this.questionRepository = questionRepository;
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
    public TranslatedCategoryDto updateCategory(TranslatedCategoryDto updatedCategory, Long id) throws CategoryNotFoundException {
        updatedCategory.getCategoryNameTranslates().forEach(languageService::setPersistedLanguageIfNotExist);
        Category category = Mapper.map(updatedCategory, Category.class);

        category.setCategoryNameTranslates(category.getCategoryNameTranslates()
                .stream()
                .map(this::persistCategoryTranslateIfNotExist)
                .collect(Collectors.toList()));

        Category categoryFromDb = getCategoryById(id);
        category.setId(categoryFromDb.getId());

        category = categoryRepository.save(category);
        return Mapper.map(category, TranslatedCategoryDto.class);
    }

    @Override
    public void deleteCategory(Long id) throws CategoryNotFoundException {
        Category category = getCategoryById(id);
        processQuestionsWithDeletedCategory(category);
        categoryRepository.delete(category);
    }

    @Override
    public Category getCategoryById(Long id) throws CategoryNotFoundException {
        return categoryRepository.findCategoryById(id).orElseThrow(() ->
                new CategoryNotFoundException("Category with id: " + id + " not found."));
    }

    public void processQuestionsWithDeletedCategory(Category category) {
        List<Question> questionsByCategory = questionRepository.findAllByCategory(category);
        questionsByCategory.forEach((question) -> question.setCategory(null));
        questionRepository.saveAll(questionsByCategory);
    }

    public LocalizedCategory persistCategoryTranslateIfNotExist(LocalizedCategory localizedCategory) {
        if (localizedCategory.getId() == null) {
            return localizedCategoryRepository.save(localizedCategory);
        }
        return localizedCategory;
    }
}
