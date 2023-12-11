package com.example.jobfinder.data.repository;

import com.example.jobfinder.data.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {


    @Query("SELECT c FROM Candidate c WHERE c.user.id = :userId")
    Optional<Candidate> findByUserId(@Param("userId") Long userId);



}
