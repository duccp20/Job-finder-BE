package com.example.jobfinder.service;


import com.example.jobfinder.data.dto.request.ChangePasswordDTO;
import com.example.jobfinder.data.dto.request.user.*;
import com.example.jobfinder.data.dto.response.ResponseMessage;
import com.example.jobfinder.data.dto.response.user.ShowUserDTO;
import com.example.jobfinder.utils.enumeration.ERole;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    Object register(UserCreationDTO userCreationDTO);

    void updateTokenActive(String to, String valueOf);

    void updateTokenForgetPassword(String to, String token);

    Object login(LoginDTO loginDTO);


    Object activeForgetPassword(String token);

    void resetPasswordByToken(ResetPasswordByToken resetPasswordByTokenDTO);

    ResponseMessage changePassword(ChangePasswordDTO changePasswordDTO);
    boolean checkValidOldPassword(String oldPass, String newPass);

    Object getUserProfile();

    UserDTO create(UserCreationDTO userCreationDTO, MultipartFile fileAvatar, ERole eRole);
    UserDTO update(long id, UserProfileDTO userProfileDTO, MultipartFile fileAvatar) throws IOException;

    Long getCurrentUserId();

    UserDTO getCurrentLoginUser();


}
