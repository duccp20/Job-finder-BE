package com.example.jobfinder.data.repository;

import com.example.jobfinder.data.entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MajorRepository extends JpaRepository<Major, Integer> {

    Major findById(int id);

    boolean existsByName(String name);

    Major findByName(String name);

}
