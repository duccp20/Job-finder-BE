package com.example.jobfinder.data.mapper;

import com.example.jobfinder.data.dto.request.candidate.CandidateDTO;
import com.example.jobfinder.data.dto.request.candidate.CandidateProfileDTO;
import com.example.jobfinder.data.dto.response.candidate.ShowCandidateDTO;
import com.example.jobfinder.data.dto.response.user.ShowUserDTO;
import com.example.jobfinder.data.entity.Candidate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = {UserMapper.class, PositionMapper.class, MajorMapper.class, ScheduleMapper.class})
@Component
public interface CandidateMapper {


    @Mapping(target = "candidatePositions", ignore = true)
    @Mapping(target = "candidateMajors", ignore = true)
    @Mapping(target = "candidateSchedules", ignore = true)
    Candidate toEntity(CandidateDTO candidateDTO);


    @Mapping(target = "majorDTOs", source = "candidateMajors")
    @Mapping(target = "positionDTOs", source = "candidatePositions")
    @Mapping(target = "scheduleDTOs", source = "candidateSchedules")
    CandidateDTO toDTO(Candidate candidate);

}