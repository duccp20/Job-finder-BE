package com.example.jobfinder.data.repository;


import com.example.jobfinder.data.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

    Role findByRoleId(Integer id);
}
