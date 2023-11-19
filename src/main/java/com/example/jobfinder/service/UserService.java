package com.example.jobfinder.service;


import com.example.jobfinder.data.dto.user.UserCreationDTO;

public interface UserService {
    Object register(UserCreationDTO userCreationDTO);
}
