package com.example.jobfinder.data.dto.request.hr;

import jakarta.validation.constraints.NotNull;

import com.example.jobfinder.data.dto.request.user.UserProfileDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class HRProfileDTO {
    @NotNull(message = "The hr's profile must not be null")
    private UserProfileDTO userProfileDTO;

    @NotNull(message = "The hr's position must not be null")
    private String position;
}
