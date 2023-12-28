package com.example.jobfinder.data.mapper;

import com.example.jobfinder.data.dto.request.candidate.CandidateApplicationDTO;
import com.example.jobfinder.data.dto.response.candidate.ApplicationDTONotShowCandidate;
import com.example.jobfinder.data.dto.response.candidate.ApplicationDTONotShowJob;
import com.example.jobfinder.data.entity.CandidateApplication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = {JobMapper.class, CandidateMapper.class})
@Component
public interface CandidateApplicationMapper {

	@Mapping(source = "applicationDTO.jobDTO",target = "job")
	@Mapping(source = "applicationDTO.candidateDTO", target = "candidate")
	CandidateApplication toEntity(CandidateApplicationDTO applicationDTO);

	 @Mapping(source = "job",target = "jobDTO")
	 @Mapping(source = "candidate",target = "candidateDTO")
	 @Mapping(source = "createdDate",target = "appliedDate")
	CandidateApplicationDTO toDTO(CandidateApplication application);

	@Mapping(source = "application.job",target = "jobDTO")
	ApplicationDTONotShowCandidate toDTONotShowCandidate(CandidateApplication application);

	@Mapping(source = "application.candidate.user.id",target = "candidateId")
	@Mapping(source = "application.candidate.user.firstName",target = "candidateFirstName")
	@Mapping(source = "application.candidate.user.lastName",target = "candidateLastName")
	@Mapping(source = "application.candidate.user.phone",target = "candidatePhoneNumber")
	@Mapping(source = "application.candidate.user.email",target = "candidateEmail")
	ApplicationDTONotShowJob toDTONotShowJob(CandidateApplication application);
}
