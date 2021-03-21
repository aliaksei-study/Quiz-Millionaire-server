package com.example.quiz.controller.v1.api;

import com.example.quiz.dto.TranslatedCategoryDto;
import com.example.quiz.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/categories")
public class CategoryController {
    private final ICategoryService categoryService;

    @Autowired
    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TranslatedCategoryDto>> getAllCategories() {
        List<TranslatedCategoryDto> categories = categoryService.getCategories();
        return ResponseEntity.ok(categories);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TranslatedCategoryDto> saveCategory(@RequestBody @Valid TranslatedCategoryDto categoryDto)
            throws URISyntaxException {
        TranslatedCategoryDto persistedCategory = categoryService.saveCategory(categoryDto);
        return ResponseEntity.created(new URI("/api/v1/categories/" + persistedCategory.getId()))
                .body(persistedCategory);
    }
}
