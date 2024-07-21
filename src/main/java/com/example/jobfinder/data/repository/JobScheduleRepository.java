package com.example.jobfinder.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jobfinder.data.entity.JobSchedule;

@Repository
public interface JobScheduleRepository extends JpaRepository<JobSchedule, Long> {

    List<JobSchedule> findAllByJob_Id(long jobId);
}
