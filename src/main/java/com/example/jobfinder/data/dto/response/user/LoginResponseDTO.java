package com.example.jobfinder.data.dto.response.user;

import com.example.jobfinder.data.dto.request.user.UserDTO;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {
    private int httpCode;
    private String message;
    private String accessToken;
    private String refreshToken;
    private UserDTO data;
}
