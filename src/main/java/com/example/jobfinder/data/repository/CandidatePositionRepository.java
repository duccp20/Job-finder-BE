package com.example.jobfinder.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jobfinder.data.entity.CandidatePosition;

@Repository
public interface CandidatePositionRepository extends JpaRepository<CandidatePosition, Long> {

    List<CandidatePosition> findAllByCandidate_Id(long candidateId);
}
