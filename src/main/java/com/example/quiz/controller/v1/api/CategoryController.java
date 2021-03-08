package com.example.quiz.controller.v1.api;

import com.example.quiz.controller.v1.request.NewCategoryCreationRequest;
import com.example.quiz.dto.CategoryDto;
import com.example.quiz.mapper.Mapper;
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
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categories = categoryService.getCategories();
        return ResponseEntity.ok(categories);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDto> saveCategory(@RequestBody @Valid NewCategoryCreationRequest request)
            throws URISyntaxException {
        CategoryDto categoryDto = categoryService.saveCategory(Mapper.map(request, CategoryDto.class));
        return ResponseEntity.created(new URI("/api/v1/categories/" + categoryDto.getId()))
                .body(categoryDto);
    }
}
