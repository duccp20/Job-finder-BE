package com.example.jobfinder.data.mapper;

import com.example.jobfinder.data.dto.request.job.JobScheduleDTO;
import com.example.jobfinder.data.dto.request.job.JobShowDTO;
import com.example.jobfinder.data.dto.request.schedule.ScheduleDTO;
import com.example.jobfinder.data.entity.JobSchedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface JobScheduleMapper {
    @Mapping(source = "job", target = "jobDTO")
    @Mapping(source = "schedule", target = "scheduleDTO")
    JobScheduleDTO toDto(JobSchedule jobMajor);
    @Mapping(source = "jobSchedule.schedule.id", target = "id")
    @Mapping(source = "jobSchedule.schedule.name", target = "name")
    ScheduleDTO toScheduleDto(JobSchedule jobSchedule);

    @Mapping(source = "jobSchedule.job.id", target = "id")
    @Mapping(source = "jobSchedule.job.name", target = "name")
    @Mapping(source = "jobSchedule.job.company", target = "companyDTO")
    @Mapping(source = "jobSchedule.job.description", target = "description")
    @Mapping(source = "jobSchedule.job.salaryMin", target = "salaryMin")
    @Mapping(source = "jobSchedule.job.salaryMax", target = "salaryMax")
    @Mapping(source = "jobSchedule.job.requirement", target = "requirement")
    @Mapping(source = "jobSchedule.job.otherInfo", target = "otherInfo")
    @Mapping(source = "jobSchedule.job.startDate", target = "startDate")
    @Mapping(source = "jobSchedule.job.endDate", target = "endDate")
    @Mapping(source = "jobSchedule.job.location", target = "location")
    @Mapping(source = "jobSchedule.job.amount", target = "amount")
    JobShowDTO toJobShowDto(JobSchedule jobSchedule);
}