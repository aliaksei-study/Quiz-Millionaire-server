package com.example.quiz.service;

import com.example.quiz.exception.LanguageNotFoundException;
import com.example.quiz.model.Language;

import java.util.Optional;

public interface ILanguageService {
    Language findLanguageByAbbreviation(String languageAbbreviation) throws LanguageNotFoundException;
}
