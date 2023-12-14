package com.example.jobfinder.data.repository;

import com.example.jobfinder.data.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Integer> {

    boolean existsByName(String name);

    Position findByName(String name);

    Position findById(int id);

    // @Query("SELECT jp FROM JobPosition jp"
    // 		+ " WHERE jp.id IN (SELECT jpd FROM JobPositionDemand jpd WHERE jpd.universityDemand.id = :universityDemandId)")
    // List<Position> findAllByUniversityDemandId(@Param("universityDemandId") int universityDemandId);


}
