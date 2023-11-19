package com.example.jobfinder.service.impl;
import com.example.jobfinder.data.dto.ResponseMessage;
import com.example.jobfinder.data.dto.user.UserCreationDTO;
import com.example.jobfinder.data.entity.Role;
import com.example.jobfinder.data.entity.Status;
import com.example.jobfinder.data.entity.User;
import com.example.jobfinder.data.mapper.UserMapper;
import com.example.jobfinder.data.repository.RoleRepository;
import com.example.jobfinder.data.repository.StatusRepository;
import com.example.jobfinder.data.repository.UserRepository;
import com.example.jobfinder.exception.ConflictException;
import com.example.jobfinder.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service

public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private UserMapper userMapper;



    @Override
    public Object register(UserCreationDTO userCreationDTO) {

        boolean existUser = userRepository.existsByEmail(userCreationDTO.getEmail());

        if (existUser) {
            throw new ConflictException(Collections.singletonMap("email", userCreationDTO.getEmail()));
        }


        Role role = roleRepository.findByRoleId(userCreationDTO.getRole());
        Status status = statusRepository.findByName("Not Active");

        User user = userMapper.toEntity(userCreationDTO);
        user.setRole(role);
        user.setStatus(status);

        return new ResponseMessage("Register Successfully!", Collections.singletonMap("user_creation", userMapper.toDTO(userRepository.save(user))));
    }
}