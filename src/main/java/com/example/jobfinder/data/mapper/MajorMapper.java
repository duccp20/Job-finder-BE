package com.example.jobfinder.data.mapper;

import com.example.jobfinder.data.dto.request.major.MajorDTO;
import com.example.jobfinder.data.entity.CandidateMajor;
import com.example.jobfinder.data.entity.JobMajor;
import com.example.jobfinder.data.entity.Major;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface MajorMapper {

	Major toEntity(MajorDTO majorDTO);

	@Mapping(source = "major.id", target = "id")
    @Mapping(source = "major.name", target = "name")
    MajorDTO toMajorDTO(CandidateMajor candidateMajor);

	@Mapping(source = "major.id", target = "id")
	@Mapping(source = "major.name", target = "name")
	MajorDTO jobMajortoMajorDto(JobMajor jobMajor);
	@IterableMapping(elementTargetType = MajorDTO.class)
	List<MajorDTO> jobMajortoMajorDto(List<JobMajor> jobMajors);

//	@Mapping(source = "major.id", target = "id")
//	@Mapping(source = "major.name", target = "name")
//	MajorDTO toMajorDto(InternshipMajor internshipMajor);


	MajorDTO toDTO(Major major);

}
