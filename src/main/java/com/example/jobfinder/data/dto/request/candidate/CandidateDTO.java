package com.example.jobfinder.data.dto.request.candidate;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CandidateDTO {

    private String university;
    private String CV;
    private String referenceLetter;
    private String desiredJob;
    private String desiredWorkingProvince;
}
