package com.example.jobfinder.service;


import com.example.jobfinder.data.dto.request.user.UserCreationDTO;

public interface UserService {
    Object register(UserCreationDTO userCreationDTO);

    void updateTokenActive(String to, String valueOf);

    void updateTokenForgetPassword(String to, String token);
}
