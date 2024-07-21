package com.example.jobfinder.service;

import java.io.IOException;

import com.example.jobfinder.data.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.example.jobfinder.data.dto.request.ChangePasswordDTO;
import com.example.jobfinder.data.dto.request.user.*;
import com.example.jobfinder.data.dto.response.ApiResponse;
import com.example.jobfinder.data.dto.response.user.LoginResponseDTO;
import com.example.jobfinder.utils.enumeration.ERole;

public interface UserService {
    default LoginResponseDTO register(UserCreationDTO userCreationDTO) {

        return null;
    }

    void updateTokenActive(String to, String valueOf);

    void updateTokenForgetPassword(String to, String token);

    default void updateRefreshToken(String email, String refreshToken) {

    }

    ResponseEntity<LoginResponseDTO> login(LoginDTO loginDTO);

    Object activeForgetPassword(String token);

    void resetPasswordByToken(ResetPasswordByToken resetPasswordByTokenDTO);

    ApiResponse changePassword(ChangePasswordDTO changePasswordDTO);

    boolean checkValidOldPassword(String oldPass, String newPass);

    Object getUserProfile();

    UserDTO create(UserCreationDTO userCreationDTO, MultipartFile fileAvatar, ERole eRole);

    UserDTO update(long id, UserProfileDTO userProfileDTO, MultipartFile fileAvatar) throws IOException;

    Long getCurrentUserId();

    UserDTO getCurrentLoginUser();

    boolean existsByEmail(String email);

    User findByEmail(String email);

    User create(User user);
}
