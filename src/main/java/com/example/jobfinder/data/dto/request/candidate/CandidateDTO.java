package com.example.jobfinder.data.dto.request.candidate;

import com.example.jobfinder.data.dto.request.user.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CandidateDTO {
    private Long id;
    private UserDTO userDTO;
    private CandidateOtherInfoDTO candidateOtherInfoDTO;
}
