package com.example.quiz.controller.v1.api;

import com.example.quiz.config.jwt.JwtProvider;
import com.example.quiz.controller.v1.JwtResponse;
import com.example.quiz.controller.v1.request.PrincipalAuthRequest;
import com.example.quiz.exception.PrincipalWithSuchEmailAlreadyExistsException;
import com.example.quiz.service.IPrincipalService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1")
public class PrincipalController {
    private IPrincipalService principalService;
    private PasswordEncoder passwordEncoder;
    private JwtProvider jwtProvider;

    public PrincipalController(IPrincipalService principalService, JwtProvider jwtProvider,
                               PasswordEncoder passwordEncoder) {
        this.principalService = principalService;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtResponse> registerPrincipal(@RequestBody @Valid PrincipalAuthRequest principalAuthRequest)
            throws PrincipalWithSuchEmailAlreadyExistsException {
        if (principalService.isPrincipalWithSuchUsernameExists(principalAuthRequest.getEmail())) {
            throw new PrincipalWithSuchEmailAlreadyExistsException(principalAuthRequest.getEmail() + " has already used");
        }
        principalService.registerPrincipal(principalAuthRequest.getEmail(),
                passwordEncoder.encode(principalAuthRequest.getPassword()));
        return ResponseEntity.ok(new JwtResponse(jwtProvider.generateToken(principalAuthRequest.getEmail())));
    }

    @PostMapping(value = "/signin", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> loginPrincipal(@RequestBody @Valid PrincipalAuthRequest principalAuthRequest) {
        if (principalService.isPrincipalWithSuchUsernameExists(principalAuthRequest.getEmail())) {
            return ResponseEntity.ok(jwtProvider.generateToken(principalAuthRequest.getEmail()));
        }
        return ResponseEntity.notFound().build();
    }
}
