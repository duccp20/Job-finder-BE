package com.example.jobfinder.service;

import java.util.List;

import com.example.jobfinder.data.dto.request.schedule.ScheduleDTO;
import com.example.jobfinder.data.entity.CandidateSchedule;

public interface CandidateScheduleService {
    boolean update(long candidateId, List<ScheduleDTO> scheduleDTOs);

    List<CandidateSchedule> findAllByCandidate_Id(long id);
}
