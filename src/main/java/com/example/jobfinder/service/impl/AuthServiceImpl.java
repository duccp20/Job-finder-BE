package com.example.jobfinder.service.impl;

import com.example.jobfinder.data.dto.request.user.UserCreationDTO;
import com.example.jobfinder.data.dto.request.user.UserDTO;
import com.example.jobfinder.data.dto.response.user.LoginResponseDTO;
import com.example.jobfinder.data.dto.response.user.ShowUserDTO;
import com.example.jobfinder.data.entity.Candidate;
import com.example.jobfinder.data.entity.Role;
import com.example.jobfinder.data.entity.Status;
import com.example.jobfinder.data.entity.User;
import com.example.jobfinder.data.mapper.UserMapper;
import com.example.jobfinder.data.repository.RoleRepository;
import com.example.jobfinder.data.repository.StatusRepository;
import com.example.jobfinder.exception.ConflictException;
import com.example.jobfinder.service.*;
import com.example.jobfinder.utils.enumeration.ERole;
import com.example.jobfinder.utils.enumeration.Estatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

//    private final FileServiceImpl fileService;

    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;

    private final StatusService statusService;

    private final UserMapper userMapper;

    private final CandidateService candidateService;

    @Override
    public ShowUserDTO handleRegister(UserCreationDTO userCreationDTO) {

        boolean existUser = userService.existsByEmail(userCreationDTO.getEmail());

        if (existUser) {
            throw new ConflictException(Collections.singletonMap("email", userCreationDTO.getEmail()));
        }

        String encodePassword = passwordEncoder.encode(userCreationDTO.getPassword());
        User user = userMapper.toEntity(userCreationDTO);
        user.setPassword(encodePassword);

        if (userCreationDTO.getRoleID() != null) {
            Role role = roleService.findById(userCreationDTO.getRoleID());
            user.setRole(role);
        }

        String notActiveStatus = Estatus.Not_Active.toString();
        Status status = statusService.findByName(notActiveStatus);
        user.setStatus(status);

        User savedUser = userService.create(user);

        if (user.getRole().getRoleId() == ERole.candidateRole) {
            Candidate candidate = Candidate.builder().user(user).build();
            candidateService.handleCreateCandidate(candidate);
        }

        return userMapper.toShowUserDTO(savedUser);
    }


}
