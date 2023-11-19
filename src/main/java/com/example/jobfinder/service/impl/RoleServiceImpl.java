package com.example.jobfinder.service.impl;


import com.example.jobfinder.data.entity.Role;
import com.example.jobfinder.data.repository.RoleRepository;
import com.example.jobfinder.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}
