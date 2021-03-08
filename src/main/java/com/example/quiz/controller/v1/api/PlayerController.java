package com.example.quiz.controller.v1.api;

import com.example.quiz.config.jwt.JwtProvider;
import com.example.quiz.controller.v1.request.LikedQuestionRequest;
import com.example.quiz.controller.v1.request.PlayerAuthRequest;
import com.example.quiz.controller.v1.response.JwtResponse;
import com.example.quiz.dto.PlayerDto;
import com.example.quiz.exception.*;
import com.example.quiz.mapper.Mapper;
import com.example.quiz.model.Player;
import com.example.quiz.service.IPlayerService;
import com.example.quiz.util.CookieUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
public class PlayerController {
    private IPlayerService playerService;
    private JwtProvider jwtProvider;
    private PasswordEncoder passwordEncoder;
    private CookieUtil cookieUtil;

    public PlayerController(IPlayerService playerService, JwtProvider jwtProvider, PasswordEncoder passwordEncoder, CookieUtil cookieUtil) {
        this.playerService = playerService;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
        this.cookieUtil = cookieUtil;
    }

    @GetMapping(value = "/players", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PlayerDto>> getAllPlayers() {
        List<PlayerDto> player = playerService.getAllPlayers();
        return ResponseEntity.ok(player);
    }

    @PostMapping(value = "/players/liked-question", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayerDto> likeQuestion(@RequestBody @Valid LikedQuestionRequest likedQuestionRequest,
                                                  @AuthenticationPrincipal Player player)
            throws QuestionNotFoundException, QuestionAlreadyLikedException {
        PlayerDto playerDto = playerService.likeQuestion(likedQuestionRequest.getQuestionId(), player);
        return ResponseEntity.ok(playerDto);
    }

    @PostMapping(value = "/players/disliked-question", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayerDto> dislikeQuestion(@RequestBody @Valid LikedQuestionRequest likedQuestionRequest,
                                                     @AuthenticationPrincipal Player player)
            throws QuestionNotFoundException, QuestionAlreadyDislikedException {
        PlayerDto playerDto = playerService.dislikeQuestion(likedQuestionRequest.getQuestionId(), player);
        return ResponseEntity.ok(playerDto);
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
    public ResponseEntity<JwtResponse> loginPrincipal(@RequestBody @Valid PlayerAuthRequest principalAuthRequest)
            throws InvalidCredentialsException {
        HttpHeaders responseHeaders = new HttpHeaders();
        Player player = (Player) playerService.loadUserByUsername(principalAuthRequest.getEmail());
        if (passwordEncoder.matches(principalAuthRequest.getPassword(), player.getPassword())) {
            String token = jwtProvider.generateToken(player.getUsername());
            responseHeaders.add(HttpHeaders.SET_COOKIE, cookieUtil.createAccessTokenCookie(token, 1000000).toString());
            return ResponseEntity.ok().headers(responseHeaders).body(new JwtResponse(token));
        }
        throw new InvalidCredentialsException("Invalid email or password");
    }

    @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayerDto> getAuthenticatedPlayer(@AuthenticationPrincipal Player player) {
        if (player == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(Mapper.map(player, PlayerDto.class));
    }
}
