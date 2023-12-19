package com.example.jobfinder.data.repository;

import com.example.jobfinder.data.entity.CandidateMajor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateMajorRepository extends JpaRepository<CandidateMajor, Long> {

    List<CandidateMajor> findAllByCandidate_Id(long candidateId);

}
