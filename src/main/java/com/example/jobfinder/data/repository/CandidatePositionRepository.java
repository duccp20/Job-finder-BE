package com.example.jobfinder.data.repository;

import com.example.jobfinder.data.entity.CandidatePosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidatePositionRepository extends JpaRepository<CandidatePosition, Long> {

    List<CandidatePosition> findAllByCandidate_Id(long candidateId);

}
