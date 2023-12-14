package com.example.jobfinder.data.repository;

import com.example.jobfinder.data.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    Schedule findById(int id);

    Schedule findByName(String name);

    boolean existsByName(String name);

}
