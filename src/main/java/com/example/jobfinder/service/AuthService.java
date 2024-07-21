package com.example.jobfinder.service;

import com.example.jobfinder.data.dto.request.user.UserCreationDTO;
import com.example.jobfinder.data.dto.response.user.LoginResponseDTO;
import com.example.jobfinder.data.dto.response.user.ShowUserDTO;

public interface AuthService {
    ShowUserDTO handleRegister(UserCreationDTO userCreationDTO);
}
