package com.example.quiz.service;

import com.example.quiz.model.Principal;
import com.example.quiz.repository.PrincipalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class PrincipalServiceImpl implements IPrincipalService {
    private PrincipalRepository principalRepository;

    @Autowired
    public PrincipalServiceImpl(PrincipalRepository principalRepository) {
        this.principalRepository = principalRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Principal> principal = principalRepository.findPrincipalByUsername(username);
        return principal.orElseThrow(() -> new UsernameNotFoundException("Principal with such email is not found"));
    }
}