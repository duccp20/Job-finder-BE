package com.example.jobfinder.data.dto.response.user;

import com.example.jobfinder.data.dto.request.user.UserDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponseDTO {
    private int httpCode;
    private String message;
    private String accessToken;
    private UserDTO data;
}
