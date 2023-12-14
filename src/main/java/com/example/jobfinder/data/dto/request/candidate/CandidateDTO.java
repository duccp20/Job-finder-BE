package com.example.jobfinder.data.dto.request.candidate;

import com.example.jobfinder.data.dto.request.major.MajorDTO;
import com.example.jobfinder.data.dto.request.position.PositionDTO;
import com.example.jobfinder.data.dto.request.schedule.ScheduleDTO;
import lombok.*;

import java.util.List;


@Getter
@Setter
public class CandidateDTO {

    private String CV;
    private String referenceLetter;
    private boolean searchable;
    private List<PositionDTO> positionDTOs;
    private List<MajorDTO> majorDTOs;

    private List<ScheduleDTO> scheduleDTOs;
    private String desiredJob;
    private String desiredWorkingProvince;
    private String university;

}
