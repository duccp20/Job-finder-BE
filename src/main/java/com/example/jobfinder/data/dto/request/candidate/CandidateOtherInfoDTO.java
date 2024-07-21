package com.example.jobfinder.data.dto.request.candidate;

import java.util.List;

import com.example.jobfinder.data.dto.request.major.MajorDTO;
import com.example.jobfinder.data.dto.request.position.PositionDTO;
import com.example.jobfinder.data.dto.request.schedule.ScheduleDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CandidateOtherInfoDTO {
    private String university;
    private String CV;
    private String referenceLetter;
    private boolean searchable; // a candidate's status permitting hr to search and contact
    private List<PositionDTO> positionDTOs;
    private List<MajorDTO> majorDTOs;
    private List<ScheduleDTO> scheduleDTOs;
    private String desiredJob;
    private String desiredWorkingProvince;
}
