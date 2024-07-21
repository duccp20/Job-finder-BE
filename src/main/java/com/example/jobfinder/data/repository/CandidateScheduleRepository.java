package com.example.jobfinder.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jobfinder.data.entity.CandidateSchedule;

@Repository
public interface CandidateScheduleRepository extends JpaRepository<CandidateSchedule, Long> {
    List<CandidateSchedule> findAllByCandidate_Id(long candidateId);
}
