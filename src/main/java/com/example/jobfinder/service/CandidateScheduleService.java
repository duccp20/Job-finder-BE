package com.example.jobfinder.service;

import com.example.jobfinder.data.dto.request.schedule.ScheduleDTO;

import java.util.List;

public interface CandidateScheduleService {
    boolean update(long candidateId, List<ScheduleDTO> scheduleDTOs);
}
