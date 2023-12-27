package com.example.jobfinder.data.dto.response.candidate;


import com.example.jobfinder.data.dto.request.candidate.CandidateDTO;
import com.example.jobfinder.data.dto.request.major.MajorDTO;
import com.example.jobfinder.data.dto.request.position.PositionDTO;
import com.example.jobfinder.data.dto.request.schedule.ScheduleDTO;
import com.example.jobfinder.data.dto.request.user.UserDTO;
import com.example.jobfinder.data.dto.response.user.ShowUserDTO;
import com.example.jobfinder.data.entity.Candidate;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowCandidateDTO {

    private UserDTO userDTO;
    private List<MajorDTO> majorDTOs;
    private List<PositionDTO> positionDTOS;
    private List<ScheduleDTO> scheduleDTOS;

}
