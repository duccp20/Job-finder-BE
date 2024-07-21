package com.example.jobfinder.data.dto.request.hr;

import jakarta.validation.constraints.NotNull;

import com.example.jobfinder.data.dto.request.user.UserCreationDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class HRCreationDTO {
    @NotNull(message = "The user's information must not be null")
    private UserCreationDTO userCreationDTO;

    @NotNull(message = "The hr's other information must not be null")
    private HROtherInfoDTO hrOtherInfoDTO;
}
