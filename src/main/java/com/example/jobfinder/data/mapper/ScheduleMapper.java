package com.example.jobfinder.data.mapper;

import com.example.jobfinder.data.dto.request.schedule.ScheduleDTO;
import com.example.jobfinder.data.entity.CandidateSchedule;
import com.example.jobfinder.data.entity.JobSchedule;
import com.example.jobfinder.data.entity.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface ScheduleMapper {

	Schedule toEntity(ScheduleDTO scheduleDTO);

	ScheduleDTO toDTO(Schedule schedule);

	@Mapping(source = "schedule.id", target = "id")
    @Mapping(source = "schedule.name", target = "name")
    ScheduleDTO toScheduleDTO(CandidateSchedule candidateSchedule);

	@Mapping(source = "schedule.id", target = "id")
	@Mapping(source = "schedule.name", target = "name")
	ScheduleDTO toDTO(JobSchedule jobSchedule);

//	@Mapping(source = "schedule.id", target = "id")
//	@Mapping(source = "schedule.name", target = "name")
//	ScheduleDTO toDTO(InternshipSchedule internshipSchedule);

}
