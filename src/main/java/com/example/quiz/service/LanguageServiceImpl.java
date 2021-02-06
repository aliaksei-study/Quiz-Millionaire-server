package com.example.quiz.service;

import com.example.quiz.exception.LanguageNotFoundException;
import com.example.quiz.model.Language;
import com.example.quiz.repository.LanguageRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class LanguageServiceImpl implements ILanguageService {
    private LanguageRepository languageRepository;

    public LanguageServiceImpl(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @Override
    public Language findLanguageByAbbreviation(String languageAbbreviation) throws LanguageNotFoundException {
        return languageRepository.findLanguageByAbbreviation(languageAbbreviation)
                .orElseThrow(() -> new LanguageNotFoundException("language with abbreviation: " + languageAbbreviation + " not found"));
    }
}
