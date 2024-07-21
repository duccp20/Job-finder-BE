package com.example.jobfinder.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.jobfinder.data.entity.CandidateMajor;

@Repository
public interface CandidateMajorRepository extends JpaRepository<CandidateMajor, Long> {

    @Query("SELECT cm FROM CandidateMajor cm WHERE cm.candidate.id = :candidateId")
    List<CandidateMajor> findAllByCandidate_Id(long candidateId);
}
