package com.example.jobfinder.data.repository;

import com.example.jobfinder.data.entity.Schedule;
import io.micrometer.observation.ObservationFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    Schedule findById(int id);

    Schedule findByName(String name);

    boolean existsByName(String name);


}
