package com.example.jobfinder.data.repository;

import com.example.jobfinder.data.entity.JobMajor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobMajorRepository extends JpaRepository<JobMajor, Long> {

    List<JobMajor> findAllByJob_Id(long jobId);

}
