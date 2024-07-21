package com.example.jobfinder.data.dto.request.job;

import java.io.Serializable;

import com.example.jobfinder.data.dto.request.candidate.CandidateDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JobCareDTO implements Serializable {
    private int id;
    private JobDTO jobDTO;
    private CandidateDTO candidateDTO;
}
