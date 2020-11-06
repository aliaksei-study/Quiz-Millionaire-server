package com.example.quiz.controller.v1.api;

import com.example.quiz.config.jwt.JwtProvider;
import com.example.quiz.controller.v1.JwtResponse;
import com.example.quiz.controller.v1.request.PlayerAuthRequest;
import com.example.quiz.dto.PlayerDto;
import com.example.quiz.exception.PlayerWithSuchEmailAlreadyExists;
import com.example.quiz.service.IPlayerService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
public class PlayerController {
    private IPlayerService playerService;
    private PasswordEncoder passwordEncoder;
    private JwtProvider jwtProvider;

    public PlayerController(IPlayerService playerService, JwtProvider jwtProvider,
                            PasswordEncoder passwordEncoder) {
        this.playerService = playerService;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "/players", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PlayerDto>> getAllPlayers() {
        List<PlayerDto> player = playerService.getAllPlayers();
        return ResponseEntity.ok(player);
    }

    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtResponse> registerPrincipal(@RequestBody @Valid PlayerAuthRequest playerAuthRequest)
            throws PlayerWithSuchEmailAlreadyExists {
        if (playerService.isPlayerWithSuchUsernameExists(playerAuthRequest.getEmail())) {
            throw new PlayerWithSuchEmailAlreadyExists(playerAuthRequest.getEmail() + " has already used");
        }
        playerService.registerPlayer(playerAuthRequest.getEmail(),
                passwordEncoder.encode(playerAuthRequest.getPassword()));
        return ResponseEntity.ok(new JwtResponse(jwtProvider.generateToken(playerAuthRequest.getEmail())));
    }

    @PostMapping(value = "/signin", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtResponse> loginPrincipal(@RequestBody @Valid PlayerAuthRequest principalAuthRequest) {
        if (playerService.isPlayerWithSuchUsernameExists(principalAuthRequest.getEmail())) {
            return ResponseEntity.ok(new JwtResponse(jwtProvider.generateToken(principalAuthRequest.getEmail())));
        }
        return ResponseEntity.notFound().build();
    }
}
