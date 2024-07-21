package com.example.jobfinder.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jobfinder.data.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    boolean existsByIdNotAndEmail(long id, String email);

    User findByTokenActive(String token);

    Optional<User> findByPasswordForgotToken(String token);

    Optional<User> findByFirstName(String firstName);
}
