package com.example.jobfinder.data.repository;

import com.example.jobfinder.data.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

	Page<Company> findAllByNameLike(String name, Pageable pageable);

	Optional<Company> findByTax(String tax);

	List<Company> findAllByStatus_Name(String statusName);

	Page<Company> findAllByStatus_Name(String statusName, Pageable pageable);

	Long countByCreatedDateBetween(Date from, Date to);


	// Lay Company theo job id
	@Query("SELECT c FROM Company c WHERE c.id = (SELECT j.company.id FROM Job j WHERE j.id = :jobId)")
	Optional<Company> findByJobId(@Param("jobId") long jobId);

	boolean existsByName(String name);

	boolean existsByTax(String tax);

	boolean existsByEmail(String email);

	boolean existsByWebsite(String website);

	boolean existsByIdNotAndName(long id, String name);

    boolean existsByIdNotAndEmail(long id, String email);

    boolean existsByIdNotAndWebsite(long id, String website);

	boolean existsByIdNotAndPhone(long id, String phone);

    boolean existsByIdNotAndTax(long id, String website);

	// statistics
	// Thong ke Company vua dang ky
	// @Query("SELECT c.name, c.createdDate FROM Company c WHERE c.createdDate >= :timeAgo")
	// List<Object[]> getNewStatistics(@Param("timeAgo") LocalDateTime timeAgo);

	// Thong ke theo status
	// @Query("SELECT c.status.name, COUNT(c.id) FROM Company c GROUP BY c.status.name ")
	// List<Object[]> getStatusStatistics();
}
