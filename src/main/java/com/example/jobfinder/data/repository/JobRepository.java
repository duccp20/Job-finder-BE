package com.example.jobfinder.data.repository;

import com.example.jobfinder.data.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long>, JpaSpecificationExecutor<Job> {

    Page<Job> findAll(Specification<Job> filter, Pageable pageable);

    @Query("SELECT j FROM Job j WHERE j.status.id = 1")
    List<Job> findJobActive();

    @Query("SELECT COUNT(j) FROM Job j WHERE MONTH(j.startDate) = :month")
    Long recruitmentNews(@Param("month") int month);

    @Query(value = "SELECT j FROM Job j WHERE j.status.id = 1 ORDER BY j.createdDate DESC")
    Page<Job> findAllActive(Pageable pageable);

    @Query("SELECT j FROM Job j WHERE j.status.id = 1 AND j.company.id = :companyId ORDER BY j.createdDate DESC")
    List<Job> findAllActiveByCompanyId(@Param("companyId") long companyId);

    @Query("SELECT j FROM Job j WHERE  j.status.id = 1 and j.company.id= :companyId ORDER BY j.createdDate DESC")
    Page<Job> findAllActiveByCompanyId(@Param("companyId") long companyId, Pageable pageable);

    @Query("SELECT j FROM Job j WHERE  j.status.id = 4 and j.company.id= :companyId ORDER BY j.createdDate DESC")
    Page<Job> findAllDisableByCompanyId(@Param("companyId") long companyId, Pageable pageable);

    @Query("SELECT j FROM Job j WHERE j.company.id= :companyId ORDER BY j.createdDate DESC")
    Page<Job> findAllByCompanyId(@Param("companyId") long companyId, Pageable pageable);

    @Query("SELECT COUNT(*) FROM Job j WHERE  j.status.id = 1 and j.company.id= :companyId")
    long countAllActiveByCompanyId(@Param("companyId") long companyId);

    @Query("SELECT COUNT(*) FROM Job j WHERE  j.status.id = 4 and j.company.id= :companyId")
    long countAllDisableByCompanyId(@Param("companyId") long companyId);

    @Query("SELECT COUNT(*) FROM Job j WHERE j.company.id= :companyId")
    long countAllByCompanyId(@Param("companyId") long companyId);

    Long countByCreatedDateBetween(LocalDateTime from, LocalDateTime to);

    @Query("SELECT j.id, j.name, j.createdDate FROM Job j WHERE j.createdDate >= :timeAgo")
    List<Object[]> getNewStatistics(@Param("timeAgo") LocalDateTime timeAgo);

    @Query("SELECT j.status.name, COUNT(j.id) FROM Job j GROUP BY j.status.name")
    List<Object[]> getStatusStatistics();

    @Query("SELECT YEAR(j.createdDate), MONTH(j.createdDate), COUNT(j) FROM Job j WHERE j.company.id = :companyId GROUP BY YEAR(j.createdDate), MONTH(j.createdDate)")
    List<Object[]> countJobsByMonth(@Param("companyId") long companyId);

    @Query("SELECT YEAR(j.createdDate), COUNT(j) FROM Job j WHERE j.company.id = :companyId GROUP BY YEAR(j.createdDate)")
    List<Object[]> countJobsByYear(@Param("companyId") long companyId);

//     @Query("SELECT j.jobPositions FROM Job j")
//     List<Object[]> findAllJob();
//     @Query("SELECT j FROM Job j WHERE j.hr.id =:hrId ORDER BY j.createdDate ASC")
//     List<Job> findAllByHRId(@Param("hrId") int hrId);

//     @Query("SELECT j FROM Job j WHERE j.hr.user.id = :userId ORDER BY j.createdDate ASC")
//     List<Job> findAllByUserId(@Param("userId") long userId);

//     @Query("SELECT j FROM Job j WHERE j.hr.user.username = :username ORDER BY j.createdDate ASC")
//     List<Job> findAllByUsername(@Param("username") String username);

//     @Query("SELECT j.jobPosition.name, COUNT(j.id) FROM Job j GROUP BY j.jobPosition.name")
//     List<Object[]> getPositionStatistics();

//     @Query("SELECT j.major.name, COUNT(j.id) FROM Job j GROUP BY j.major.name")
//     List<Object[]> getMajorStatistics();

//     @Query("SELECT j FROM Job j"
//             + " WHERE j.hr.company.id = :companyId"
//             + " AND (:quickSearch IS NULL"
//             + "     OR (j.name LIKE %:quickSearch%"
//             + "         OR CONCAT(j.hr.user.lastName, ' ', j.hr.user.firstName) LIKE %:quickSearch%))" // full name like
//                                                                                                        // quickSearch
//             + " AND (:provinceName IS NULL OR j.location.district.province.name = :provinceName)"
//             + " AND (:endDate IS NULL OR DATE(j.endDate) = DATE(:endDate))"
//             + " AND (:statusId IS NULL OR j.status.id = :statusId)")
//     Page<Job> filterPostedJobOfCompanyByHr(
//             @Param("companyId") int companyId,
//             @Param("quickSearch") String quickSearch,
//             @Param("provinceName") String provinceName,
//             @Param("endDate") LocalDateTime endDate,
//             @Param("statusId") Integer statusId,
//             Pageable pageable);

}
