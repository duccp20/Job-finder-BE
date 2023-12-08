package com.example.jobfinder.data.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {
    private int httpCode;
    private String message;
    private String accessToken;
    private String refreshToken;
    private ShowUserDTO data;
}
