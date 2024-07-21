package com.example.jobfinder.data.dto.request.candidate;

import jakarta.validation.constraints.NotNull;

import com.example.jobfinder.data.dto.request.user.UserProfileDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CandidateProfileDTO {
    @NotNull(message = "The candidate profile must not be null")
    private UserProfileDTO userProfileDTO;

    private CandidateOtherInfoDTO candidateOtherInfoDTO;
}
