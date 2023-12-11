package com.example.jobfinder.data.dto.request.candidate;

import com.example.jobfinder.data.dto.request.user.UserProfileDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidateProfileDTO {

    private UserProfileDTO userProfileDTO;
    private CandidateDTO candidateDTO;
}
