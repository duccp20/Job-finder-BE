package com.example.jobfinder.data.dto.request.job;

import com.example.jobfinder.data.dto.request.major.MajorDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JobMajorDTO {
    private JobDTO jobDTO;

    private MajorDTO majorDTO;
}