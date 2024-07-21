package com.example.jobfinder.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.jobfinder.data.entity.HR;

@Repository
public interface HRRepository extends JpaRepository<HR, Long> {

    Optional<HR> findByUser_Id(long userId);

    @Query("SELECT hr FROM HR hr WHERE hr.user.email = :username")
    Optional<HR> findByUsername(@Param("username") String username);
}
