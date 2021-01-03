package com.example.quiz.service;

import com.example.quiz.dto.PlayerDto;
import com.example.quiz.exception.PlayerNotFoundException;
import com.example.quiz.exception.QuestionAlreadyDislikedException;
import com.example.quiz.exception.QuestionAlreadyLikedException;
import com.example.quiz.exception.QuestionNotFoundException;
import com.example.quiz.mapper.Mapper;
import com.example.quiz.model.Player;
import com.example.quiz.model.Question;
import com.example.quiz.model.enumeration.Role;
import com.example.quiz.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PlayerServiceImpl implements IPlayerService {
    private PlayerRepository playerRepository;
    private IQuestionService questionService;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, IQuestionService questionService) {
        this.playerRepository = playerRepository;
        this.questionService = questionService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Player> player = playerRepository.findPlayerByUsername(username);
        return player.orElseThrow(() -> new UsernameNotFoundException("Principal with such email is not found"));
    }

    @Override
    public boolean isPlayerWithSuchUsernameExists(String username) {
        Optional<Player> player = playerRepository.findPlayerByUsername(username);
        return player.isPresent();
    }

    @Override
    public void registerPlayer(String email, String encodedPassword) {
        Player player = new Player(email, encodedPassword, Role.PLAYER);
        playerRepository.save(player);
    }

    @Override
    public List<PlayerDto> getAllPlayers() {
        return Mapper.mapAll(playerRepository.findAll(), PlayerDto.class);
    }

    @Override
    public PlayerDto likeQuestion(Long questionId, Player player) throws QuestionNotFoundException, QuestionAlreadyLikedException {
        Question likedQuestion = questionService.getQuestionById(questionId);
        List<Question> playerLikedQuestions = player.getLikedQuestions();
        List<Question> playerDislikedQuestions = player.getDislikedQuestions();
        if (!playerLikedQuestions.contains(likedQuestion)) {
            playerDislikedQuestions.remove(likedQuestion);
            playerLikedQuestions.add(likedQuestion);
            player = playerRepository.save(player);
        } else {
            throw new QuestionAlreadyLikedException("question with id: " + questionId + " already liked");
        }
        return Mapper.map(player, PlayerDto.class);
    }

    @Override
    public PlayerDto dislikeQuestion(Long questionId, Player player) throws QuestionNotFoundException, QuestionAlreadyDislikedException {
        Question dislikedQuestion = questionService.getQuestionById(questionId);
        List<Question> playerLikedQuestions = player.getLikedQuestions();
        List<Question> playerDislikedQuestions = player.getDislikedQuestions();
        if (!playerDislikedQuestions.contains(dislikedQuestion)) {
            playerLikedQuestions.remove(dislikedQuestion);
            playerDislikedQuestions.add(dislikedQuestion);
            player = playerRepository.save(player);
        } else {
            throw new QuestionAlreadyDislikedException("question with id: " + questionId + " already disliked");
        }
        return Mapper.map(player, PlayerDto.class);
    }

    @Override
    public void deletePlayers(List<Long> playerIds) throws PlayerNotFoundException {

    }

    public void processQuestionsWithDeletedPlayerIds(List<Long> playerIds) {
    }
}
