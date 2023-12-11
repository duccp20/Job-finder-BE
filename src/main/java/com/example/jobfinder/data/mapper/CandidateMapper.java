package com.example.jobfinder.data.mapper;

import com.example.jobfinder.data.dto.request.candidate.CandidateDTO;
import com.example.jobfinder.data.dto.request.candidate.CandidateProfileDTO;
import com.example.jobfinder.data.dto.response.candidate.ShowCandidateDTO;
import com.example.jobfinder.data.dto.response.user.ShowUserDTO;
import com.example.jobfinder.data.entity.Candidate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface CandidateMapper {

    @Mapping(target = "showUserDTO", source = "showUserDTO")
    ShowCandidateDTO toShowCandidateDTO(ShowUserDTO showUserDTO, CandidateDTO candidate);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "searchable", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "candidatePositions", ignore = true)
    @Mapping(target = "candidateMajors", ignore = true)
    Candidate toEntity(CandidateDTO candidateDTO);

    CandidateDTO toDTO(Candidate candidate);
}
