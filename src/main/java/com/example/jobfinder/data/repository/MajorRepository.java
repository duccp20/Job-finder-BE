package com.example.jobfinder.data.repository;

import com.example.jobfinder.data.entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MajorRepository extends JpaRepository<Major, Integer> {


    Optional<Major> findById(Integer integer);

    boolean existsByName(String name);

    Major findByName(String name);

}
