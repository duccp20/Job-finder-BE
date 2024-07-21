package com.example.jobfinder.data.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import com.example.jobfinder.data.dto.request.job.JobShowDTO;
import com.example.jobfinder.data.dto.request.position.PositionDTO;
import com.example.jobfinder.data.entity.JobPosition;

@Mapper(componentModel = "spring")
@Component
public interface JobPositionMapper {
    @Mapping(source = "jobPosition.position.id", target = "id")
    @Mapping(source = "jobPosition.position.name", target = "name")
    PositionDTO toPositionDto(JobPosition jobPosition);

    @Mapping(source = "jobPosition.job.id", target = "id")
    @Mapping(source = "jobPosition.job.name", target = "name")
    @Mapping(source = "jobPosition.job.company", target = "companyDTO")
    @Mapping(source = "jobPosition.job.description", target = "description")
    @Mapping(source = "jobPosition.job.salaryMin", target = "salaryMin")
    @Mapping(source = "jobPosition.job.salaryMax", target = "salaryMax")
    @Mapping(source = "jobPosition.job.requirement", target = "requirement")
    @Mapping(source = "jobPosition.job.otherInfo", target = "otherInfo")
    @Mapping(source = "jobPosition.job.startDate", target = "startDate")
    @Mapping(source = "jobPosition.job.endDate", target = "endDate")
    @Mapping(source = "jobPosition.job.location", target = "location")
    @Mapping(source = "jobPosition.job.amount", target = "amount")
    JobShowDTO toJobShowDto(JobPosition jobPosition);
}
