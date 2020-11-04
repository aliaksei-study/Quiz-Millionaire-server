package com.example.quiz.service;

import com.example.quiz.exception.AnswerNotFoundException;
import com.example.quiz.model.Answer;
import com.example.quiz.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class AnswerServiceImpl implements IAnswerService {
    private final AnswerRepository answerRepository;

    @Autowired
    public AnswerServiceImpl(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Override
    public void addAnswer(Answer answer) {

    }

    @Override
    public void updateAnswer(Answer newAnswer, Long id) {

    }

    @Override
    public void deleteAnswer(Long id) {

    }

    @Override
    public Answer getAnswerById(Long answerId) throws AnswerNotFoundException {
        return answerRepository.findById(answerId).orElseThrow(() ->
                new AnswerNotFoundException("answer with id " + answerId + " is not exist"));
    }

    @Override
    public List<Answer> getAnswers() {
        return List.of(new Answer());
    }
}
