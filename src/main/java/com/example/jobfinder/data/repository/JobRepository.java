package com.example.jobfinder.data.repository;

import com.example.jobfinder.data.dto.request.job.JobCreationDTO;
import com.example.jobfinder.data.dto.request.job.JobFilterDTO;
import com.example.jobfinder.data.dto.request.job.JobShowDTO;
import com.example.jobfinder.data.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, Long>, JpaSpecificationExecutor<Job> {

    Page<Job> findAll(Specification<Job> filter, Pageable pageable);

    /**
     * Finds jobs with similar details, except start and end dates.
     *
     * @param name        The name of the job.
     * @param location    The location of the job.
     * @param province    The province of the job.
     * @param description The description of the job.
     * @param requirement The requirement of the job.
     * @param otherInfo   Other information about the job.
     * @param salaryMin   The minimum salary of the job.
     * @param salaryMax   The maximum salary of the job.
     * @param amount      The amount of the job.
     * @param startDate   The start date of the job.
     * @param endDate     The end date of the job.
     * @return A list of jobs with similar details, except start and end dates.
     */
    @Query("SELECT j FROM Job j " +
            "WHERE j.name = :name " +
            "AND j.location = :location " +
            "AND j.province = :province " +
            "AND j.description = :description " +
            "AND j.requirement = :requirement " +
            "AND j.otherInfo = :otherInfo " +
            "AND j.salaryMin = :salaryMin " +
            "AND j.salaryMax = :salaryMax " +
            "AND j.amount = :amount " +
            "AND j.startDate != :startDate " +
            "AND j.endDate != :endDate")
    List<Job> findJobsWithSimilarDetailsExceptStartAndEndDate(
            @Param("name") String name,
            @Param("location") String location,
            @Param("province") String province,
            @Param("description") String description,
            @Param("requirement") String requirement,
            @Param("otherInfo") String otherInfo,
            @Param("salaryMin") Long salaryMin,
            @Param("salaryMax") Long salaryMax,
            @Param("amount") Integer amount,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);



   boolean existsByStartDate(Date startDate);

    @Query("SELECT j FROM Job j WHERE j.status.statusId = 1")
    List<Job> findJobActive();

    @Query("SELECT COUNT(j) FROM Job j WHERE MONTH(j.startDate) = :month")
    Long recruitmentNews(@Param("month") int month);

    @Query(value = "SELECT j FROM Job j WHERE j.status.statusId = 1 ORDER BY j.createdDate DESC")
    Page<Job> findAllActive(Pageable pageable);

    @Query("SELECT j FROM Job j WHERE j.status.statusId = 1 AND j.company.id = :companyId ORDER BY j.createdDate DESC")
    List<Job> findAllActiveByCompanyId(@Param("companyId") long companyId);

    @Query("SELECT j FROM Job j WHERE  j.status.statusId = 1 and j.company.id= :companyId ORDER BY j.createdDate DESC")
    Page<Job> findAllActiveByCompanyId(@Param("companyId") long companyId, Pageable pageable);

    @Query("SELECT j FROM Job j WHERE  j.status.statusId = 4 and j.company.id= :companyId ORDER BY j.createdDate DESC")
    Page<Job> findAllDisableByCompanyId(@Param("companyId") long companyId, Pageable pageable);

    @Query("SELECT j FROM Job j WHERE j.company.id= :companyId AND j.status.statusId = 1 AND j.status.statusId = 4 ORDER BY j.createdDate DESC ")
    Page<Job> findAllByCompanyId(@Param("companyId") long companyId, Pageable pageable);

    @Query("SELECT COUNT(*) FROM Job j WHERE  j.status.statusId = 1 and j.company.id= :companyId")
    long countAllActiveByCompanyId(@Param("companyId") long companyId);

    @Query("SELECT COUNT(*) FROM Job j WHERE  j.status.statusId = 4 and j.company.id= :companyId")
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

 @Query("SELECT DISTINCT j FROM Job j " +
         "LEFT JOIN j.jobPositions jp " +
         "LEFT JOIN j.jobSchedules js " +
         "LEFT JOIN j.jobMajors jm " +
         "WHERE (j.name LIKE CONCAT('%', :name, '%')) " +
         "AND (:provinceName IS NULL OR j.province LIKE CONCAT('%', :provinceName, '%')) " +
         "AND (COALESCE(SIZE(:positionIds), 0) = 0 OR jp.position.id IN :positionIds) " +
         "AND (COALESCE(SIZE(:scheduleIds), 0) = 0 OR js.schedule.id IN :scheduleIds) " +
         "AND (COALESCE(SIZE(:majorIds), 0) = 0 OR jm.major.id IN :majorIds) " +
         "AND j.status.statusId = 1 " +
         "ORDER BY j.createdDate DESC")
 Page<Job> findJobsWithFilters(@Param("name") String name,
                               @Param("provinceName") String provinceName,
                               @Param("positionIds") List<Integer> positionIds,
                               @Param("scheduleIds") List<Integer> scheduleIds,
                               @Param("majorIds") List<Integer> majorIds,
                               Pageable pageable);



//     @Query("SELECT j.jobPositions FROM Job j")
//     List<Object[]> findAllJob();
//     @Query("SELECT j FROM Job j WHERE j.hr.id =:hrId ORDER BY j.createdDate ASC")
//     List<Job> findAllByHRId(@Param("hrId") int hrId);
//
//     @Query("SELECT j FROM Job j WHERE j.hr.user.id = :userId ORDER BY j.createdDate ASC")
//     List<Job> findAllByUserId(@Param("userId") long userId);
//
//     @Query("SELECT j FROM Job j WHERE j.hr.user.username = :username ORDER BY j.createdDate ASC")
//     List<Job> findAllByUsername(@Param("username") String username);
//
//     @Query("SELECT j.jobPosition.name, COUNT(j.id) FROM Job j GROUP BY j.jobPosition.name")
//     List<Object[]> getPositionStatistics();
//
//     @Query("SELECT j.major.name, COUNT(j.id) FROM Job j GROUP BY j.major.name")
//     List<Object[]> getMajorStatistics();
//
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
