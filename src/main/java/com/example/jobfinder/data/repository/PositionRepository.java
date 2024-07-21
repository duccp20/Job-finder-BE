package com.example.jobfinder.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jobfinder.data.entity.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

    boolean existsByName(String name);

    Position findByName(String name);

    Position findById(int id);

    // @Query("SELECT jp FROM JobPosition jp"
    // 		+ " WHERE jp.id IN (SELECT jpd FROM JobPositionDemand jpd WHERE jpd.universityDemand.id =
    // :universityDemandId)")
    // List<Position> findAllByUniversityDemandId(@Param("universityDemandId") int universityDemandId);

}
