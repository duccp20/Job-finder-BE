package com.example.jobfinder.data.repository;

import com.example.jobfinder.data.entity.JobSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobScheduleRepository extends JpaRepository<JobSchedule, Long> {

    List<JobSchedule> findAllByJob_Id(long jobId);

}
