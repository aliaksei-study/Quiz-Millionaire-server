package com.example.quiz.service;

import com.example.quiz.dto.PlayerDto;
import com.example.quiz.mapper.Mapper;
import com.example.quiz.model.Player;
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

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
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
}
