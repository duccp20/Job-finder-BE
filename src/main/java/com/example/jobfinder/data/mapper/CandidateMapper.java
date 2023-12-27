package com.example.jobfinder.data.mapper;

import com.example.jobfinder.data.dto.request.candidate.CandidateCreationDTO;
import com.example.jobfinder.data.dto.request.candidate.CandidateDTO;
import com.example.jobfinder.data.dto.request.candidate.CandidateProfileDTO;
import com.example.jobfinder.data.entity.Candidate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring", uses = { UserMapper.class,
        PositionMapper.class, ScheduleMapper.class, MajorMapper.class})
@Component
public interface CandidateMapper {

    @Mapping(source = "userCreationDTO", target = "user")
    @Mapping(constant = "true", target = "searchable")
    @Mapping(source = "candidateOtherInfoDTO.CV", target = "CV")
    @Mapping(source = "candidateOtherInfoDTO.referenceLetter", target = "referenceLetter")
    @Mapping(source = "candidateOtherInfoDTO.university", target = "university")
    @Mapping(source = "candidateOtherInfoDTO.desiredJob", target = "desiredJob")
    @Mapping(source = "candidateOtherInfoDTO.desiredWorkingProvince", target = "desiredWorkingProvince")
    Candidate toEntity(CandidateCreationDTO candidateCreationDTO);

    @Mapping(source = "userProfileDTO", target = "user")
    @Mapping(source = "candidateOtherInfoDTO.searchable", target = "searchable")
    @Mapping(source = "candidateOtherInfoDTO.CV", target = "CV")
    @Mapping(source = "candidateOtherInfoDTO.referenceLetter", target = "referenceLetter")
    @Mapping(source = "candidateOtherInfoDTO.university", target = "university")
    @Mapping(source = "candidateOtherInfoDTO.desiredJob", target = "desiredJob")
    @Mapping(source = "candidateOtherInfoDTO.desiredWorkingProvince", target = "desiredWorkingProvince")
    Candidate toEntity(CandidateProfileDTO candidateProfileDTO);

    @Mapping(source = "user", target = "userDTO")
    @Mapping(source = "searchable", target = "candidateOtherInfoDTO.searchable")
    @Mapping(source = "CV", target = "candidateOtherInfoDTO.CV")
    @Mapping(source = "referenceLetter", target = "candidateOtherInfoDTO.referenceLetter")
    @Mapping(source = "university", target = "candidateOtherInfoDTO.university")
    @Mapping(source = "desiredJob", target = "candidateOtherInfoDTO.desiredJob")
    @Mapping(source = "desiredWorkingProvince", target = "candidateOtherInfoDTO.desiredWorkingProvince")
    @Mapping(source = "candidatePositions", target = "candidateOtherInfoDTO.positionDTOs")
    @Mapping(source = "candidateSchedules", target = "candidateOtherInfoDTO.scheduleDTOs")
    @Mapping(source = "candidateMajors", target = "candidateOtherInfoDTO.majorDTOs")
    CandidateDTO toDTO(Candidate candidate);

    @Mapping(target = "userDTO", ignore = true)
    @Mapping(source = "searchable", target = "candidateOtherInfoDTO.searchable")
    @Mapping(source = "CV", target = "candidateOtherInfoDTO.CV")
    @Mapping(source = "referenceLetter", target = "candidateOtherInfoDTO.referenceLetter")
    @Mapping(source = "university", target = "candidateOtherInfoDTO.university")
    @Mapping(source = "desiredJob", target = "candidateOtherInfoDTO.desiredJob")
    @Mapping(source = "desiredWorkingProvince", target = "candidateOtherInfoDTO.desiredWorkingProvince")
    @Mapping(source = "candidatePositions", target = "candidateOtherInfoDTO.positionDTOs")
    @Mapping(source = "candidateSchedules", target = "candidateOtherInfoDTO.scheduleDTOs")
    @Mapping(source = "candidateMajors", target = "candidateOtherInfoDTO.majorDTOs")
    CandidateDTO toShowDTO(Candidate candidate);

    @Mapping(source = "userDTO", target = "user")
    @Mapping(source = "candidateOtherInfoDTO.searchable", target = "searchable")
    @Mapping(source = "candidateOtherInfoDTO.CV", target = "CV")
    @Mapping(source = "candidateOtherInfoDTO.referenceLetter", target = "referenceLetter")
    @Mapping(source = "candidateOtherInfoDTO.university", target = "university")
    @Mapping(source = "candidateOtherInfoDTO.desiredJob", target = "desiredJob")
    @Mapping(source = "candidateOtherInfoDTO.desiredWorkingProvince", target = "desiredWorkingProvince")
    Candidate toEntity(CandidateDTO candidateDTO);
}