package com.example.jobfinder.data.repository;

import com.example.jobfinder.data.entity.CandidateSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateScheduleRepository extends JpaRepository<CandidateSchedule, Long> {
    List<CandidateSchedule> findAllByCandidate_Id(long candidateId);
}
