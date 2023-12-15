package com.example.jobfinder.data.repository;

import com.example.jobfinder.data.entity.JobPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobPositionRepository extends JpaRepository<JobPosition, Long> {

    List<JobPosition> findAllByJob_Id(long jobId);

}
