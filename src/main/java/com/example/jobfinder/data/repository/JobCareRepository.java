package com.example.jobfinder.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.jobfinder.data.entity.Job;
import com.example.jobfinder.data.entity.JobCare;

@Repository
public interface JobCareRepository extends JpaRepository<JobCare, Long> {

    boolean existsByCandidateIdAndJobId(Long candidateId, Long jobId);

    @Query("SELECT jc FROM JobCare jc WHERE jc.candidate.id = :candidateId AND jc.job.id = :jobId")
    Optional<JobCare> findByCandidateIdAndJobId(@Param("candidateId") long candidateId, @Param("jobId") long jobId);

    @Query("SELECT jc FROM JobCare jc WHERE jc.candidate.id = :candidateId ORDER BY jc.id DESC")
    Page<JobCare> findAllByCandidateId(@Param("candidateId") long candidateId, Pageable pageable);

    @Query("SELECT jc.job.id FROM JobCare jc WHERE jc.candidate.id = :candidateId GROUP BY jc.job.id")
    List<Integer> finJobSave(@Param("candidateId") long candidateId);

    void deleteJobCareByJob(Job job);

    // @Query("SELECT jc FROM JobCare jc WHERE jc.job.id = :jobId")
    // Page<JobCare> findAllByJobId(@Param("jobId") int jobId, Pageable pageable);

    //     @Query("SELECT CASE WHEN COUNT(jc.id) > 0 THEN TRUE ELSE FALSE END FROM JobCare jc"
    //             + " WHERE jc.candidate.id = :candidateId AND jc.job.id = :jobId")
    //     boolean existsByCandidateIdAndJobId(@Param("candidateId") long candidateId, @Param("jobId") long jobId);

}
