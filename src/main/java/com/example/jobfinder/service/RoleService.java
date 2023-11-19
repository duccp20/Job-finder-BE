package com.example.jobfinder.service;

import com.example.jobfinder.data.entity.Role;


public interface RoleService {

    Role findByName(String name);
}
