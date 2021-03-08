package com.example.quiz.service;

import com.example.quiz.dto.LanguageDto;
import com.example.quiz.exception.LanguageNotFoundException;
import com.example.quiz.mapper.Mapper;
import com.example.quiz.model.Language;
import com.example.quiz.repository.LanguageRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class LanguageServiceImpl implements ILanguageService {
    private final String DEFAULT_LANGUAGE_ABBREVIATION = "en-US";

    private final LanguageRepository languageRepository;

    public LanguageServiceImpl(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @Override
    public Optional<Language> findLanguageByAbbreviation(String languageAbbreviation) {
        return languageRepository.findLanguageByAbbreviation(languageAbbreviation);
    }

    @Override
    public Language getDefaultLanguage() throws LanguageNotFoundException {
        return findLanguageByAbbreviation(DEFAULT_LANGUAGE_ABBREVIATION)
                .orElseThrow(() -> new LanguageNotFoundException("language with abbreviation: " +
                        DEFAULT_LANGUAGE_ABBREVIATION + " not found"));
    }

    @Override
    public LanguageDto saveLanguage(LanguageDto languageDto) {
        Language language = Mapper.map(languageDto, Language.class);
        language = languageRepository.save(language);
        return Mapper.map(language, LanguageDto.class);
    }
}
