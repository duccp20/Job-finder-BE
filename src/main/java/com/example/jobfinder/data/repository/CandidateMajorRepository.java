package com.example.jobfinder.data.repository;

import com.example.jobfinder.data.entity.CandidateMajor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateMajorRepository extends JpaRepository<CandidateMajor, Long> {

    @Query("SELECT cm FROM CandidateMajor cm WHERE cm.candidate.id = :candidateId")
    List<CandidateMajor> findAllByCandidate_Id(long candidateId);

}
