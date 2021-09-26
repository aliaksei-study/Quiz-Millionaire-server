package com.example.quiz.service;

import com.example.quiz.dto.LanguageDto;
import com.example.quiz.dto.TranslatedTextDto;
import com.example.quiz.exception.LanguageNotFoundException;
import com.example.quiz.model.Language;

import java.util.Optional;

public interface ILanguageService {
    Optional<Language> findLanguageByAbbreviation(String languageAbbreviation);
    Language getDefaultLanguage() throws LanguageNotFoundException;
    LanguageDto saveLanguage(LanguageDto languageDto);
    void setPersistedLanguageIfNotExist(TranslatedTextDto translate);
}
