package com.example.jobfinder.data.repository;


import com.example.jobfinder.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    boolean existsByIdNotAndEmail(long id, String email);

    User findByTokenActive(String token);
    Optional<User> findByPasswordForgotToken(String token);

}
