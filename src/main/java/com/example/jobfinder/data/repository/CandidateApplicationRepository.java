package com.example.jobfinder.data.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.jobfinder.data.entity.CandidateApplication;

@Repository
public interface CandidateApplicationRepository extends JpaRepository<CandidateApplication, Long> {

    Page<CandidateApplication> findAllByCandidateIdOrderByCreatedDateDesc(
            @Param("candidateId") long candidateId, Pageable pageable);

    @Query("SELECT ca FROM CandidateApplication ca WHERE ca.job.id = :jobId ORDER BY ca.createdDate DESC")
    Page<CandidateApplication> findAllByJobId(@Param("jobId") long jobId, Pageable pageable);

    @Query("SELECT CASE WHEN COUNT(ca.id) > 0 THEN TRUE ELSE FALSE END FROM CandidateApplication ca"
            + " WHERE ca.candidate.id = :candidateId AND ca.job.id = :jobId")
    boolean existsByCandidateIdAndJobId(@Param("candidateId") long candidateId, @Param("jobId") long jobId);

    @Query(
            "SELECT YEAR(ca.createdDate), MONTH(ca.createdDate), COUNT(ca) FROM CandidateApplication ca JOIN ca.job j WHERE j.company.id = :companyId GROUP BY YEAR(ca.createdDate), MONTH(ca.createdDate)")
    List<Object[]> countApplicationsByMonth(@Param("companyId") long companyId);

    @Query(
            "SELECT YEAR(ca.createdDate), COUNT(ca) FROM CandidateApplication ca JOIN ca.job j WHERE j.company.id = :companyId GROUP BY YEAR(ca.createdDate)")
    List<Object[]> countApplicationsByYear(@Param("companyId") long companyId);

    // @Query("SELECT ca FROM CandidateApplication ca WHERE ca.candidate.user.username = :username ORDER BY
    // ca.createdDate DESC")
    // Page<CandidateApplication> findAllByUsername(@Param("username") String username, Pageable pageable);

    // @Query("SELECT COUNT(ca) FROM CandidateApplication ca WHERE ca.job.id = :jobId")
    // int countByJobId(@Param("jobId") int jobId);
}
