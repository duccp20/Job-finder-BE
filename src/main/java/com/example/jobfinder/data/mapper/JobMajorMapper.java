package com.example.jobfinder.data.mapper;


import com.example.jobfinder.data.dto.request.job.JobShowDTO;
import com.example.jobfinder.data.dto.request.major.MajorDTO;
import com.example.jobfinder.data.entity.JobMajor;
import com.example.jobfinder.data.entity.Major;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface JobMajorMapper {
    MajorDTO toMajorDto(Major major);

    @Mapping(source = "jobMajor.major.id", target = "id")
    @Mapping(source = "jobMajor.major.name", target = "name")
    MajorDTO toMajorDto(JobMajor jobMajor);

    @Mapping(source = "jobMajor.job.id", target = "id")
    @Mapping(source = "jobMajor.job.name", target = "name")
    @Mapping(source = "jobMajor.job.company", target = "companyDTO")
    @Mapping(source = "jobMajor.job.description", target = "description")
    @Mapping(source = "jobMajor.job.salaryMin", target = "salaryMin")
    @Mapping(source = "jobMajor.job.salaryMax", target = "salaryMax")
    @Mapping(source = "jobMajor.job.requirement", target = "requirement")
    @Mapping(source = "jobMajor.job.otherInfo", target = "otherInfo")
    @Mapping(source = "jobMajor.job.startDate", target = "startDate")
    @Mapping(source = "jobMajor.job.endDate", target = "endDate")
    @Mapping(source = "jobMajor.job.location", target = "location")
    @Mapping(source = "jobMajor.job.amount", target = "amount")
    JobShowDTO toJobDto(JobMajor jobMajor);



    @IterableMapping(elementTargetType = MajorDTO.class)
    List<MajorDTO> toMajorDtoList(List<JobMajor> jobMajors);

}
