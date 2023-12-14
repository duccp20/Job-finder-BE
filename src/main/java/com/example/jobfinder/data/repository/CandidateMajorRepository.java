package com.example.jobfinder.data.repository;

import com.example.jobfinder.data.entity.CandidateMajor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidateMajorRepository extends JpaRepository<CandidateMajor, Long> {

    List<CandidateMajor> findAllByCandidate_Id(long candidateId);

}
