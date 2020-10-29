package com.example.quiz.repository;

import com.example.quiz.model.Principal;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PrincipalRepository extends CrudRepository<Principal, Long> {
    Optional<Principal> findPrincipalByUsername(String username);
}
