package com.example.jobfinder.data.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.jobfinder.data.dto.request.job.JobCareDTO;
import com.example.jobfinder.data.entity.JobCare;

@Mapper(componentModel = "spring", uses = JobMapper.class)
public interface JobCareMapper {
    @Mapping(source = "candidateDTO", target = "candidate")
    @Mapping(source = "jobDTO", target = "job")
    JobCare toEntity(JobCareDTO jobCareDTO);

    @Mapping(source = "job", target = "jobDTO")
    @Mapping(source = "candidate", target = "candidateDTO")
    JobCareDTO toDTO(JobCare jobCare);
}
