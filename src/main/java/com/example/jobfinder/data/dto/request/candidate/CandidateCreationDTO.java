package com.example.jobfinder.data.dto.request.candidate;

import com.example.jobfinder.data.dto.request.user.UserCreationDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@Data
@NoArgsConstructor
public class CandidateCreationDTO {
    @NotNull(message = "The user's information must not be null")
    private UserCreationDTO userCreationDTO;
    private CandidateOtherInfoDTO candidateOtherInfoDTO;
}
