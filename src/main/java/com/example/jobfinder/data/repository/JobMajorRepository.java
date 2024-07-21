package com.example.jobfinder.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jobfinder.data.entity.JobMajor;

@Repository
public interface JobMajorRepository extends JpaRepository<JobMajor, Long> {

    List<JobMajor> findAllByJob_Id(long jobId);
}
