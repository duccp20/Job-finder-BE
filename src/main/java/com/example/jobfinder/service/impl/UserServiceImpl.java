package com.example.jobfinder.service.impl;
import com.example.jobfinder.data.dto.response.ResponseMessage;
import com.example.jobfinder.data.dto.request.user.UserCreationDTO;
import com.example.jobfinder.data.entity.Role;
import com.example.jobfinder.data.entity.Status;
import com.example.jobfinder.data.entity.User;
import com.example.jobfinder.data.mapper.UserMapper;
import com.example.jobfinder.data.repository.RoleRepository;
import com.example.jobfinder.data.repository.StatusRepository;
import com.example.jobfinder.data.repository.UserRepository;
import com.example.jobfinder.exception.ConflictException;
import com.example.jobfinder.exception.ResourceNotFoundException;
import com.example.jobfinder.service.UserService;

import com.example.jobfinder.utils.enumeration.Estatus;
import jakarta.servlet.http.HttpServletRequest;
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


    @Autowired
    private HttpServletRequest servletRequest;

    //get url


    @Override
    public Object register(UserCreationDTO userCreationDTO) {

        boolean existUser = userRepository.existsByEmail(userCreationDTO.getEmail());

        if (existUser) {
            throw new ConflictException(Collections.singletonMap("email", userCreationDTO.getEmail()));
        }

        User user = userMapper.toEntity(userCreationDTO);

        Role role = roleRepository.findByRoleId(userCreationDTO.getRole());
        user.setRole(role);

        String notActiveStatus = Estatus.Not_Active.toString();
        Status status = statusRepository.findByName(notActiveStatus);
        user.setStatus(status);

//        user.setSocialAccount(null);

        userRepository.save(user);
        return new ResponseMessage(201, "Register Successfully", servletRequest.getServletPath());
    }

    @Override
    public void updateTokenActive(String email, String token) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> {
            throw new ResourceNotFoundException(Collections.singletonMap("email", email));
        });
        user.setTokenActive(token);
        userRepository.save(user);
    }

    @Override
    public void updateTokenForgetPassword(String email, String token) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> {
            throw new ResourceNotFoundException(Collections.singletonMap("email", email));
        });
        user.setPasswordForgotToken(token);
        userRepository.save(user);
    }

}

