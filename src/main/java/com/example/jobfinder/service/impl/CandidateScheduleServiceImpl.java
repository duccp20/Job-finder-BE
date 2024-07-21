package com.example.jobfinder.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jobfinder.data.dto.request.schedule.ScheduleDTO;
import com.example.jobfinder.data.entity.Candidate;
import com.example.jobfinder.data.entity.CandidateSchedule;
import com.example.jobfinder.data.entity.Schedule;
import com.example.jobfinder.data.repository.CandidateScheduleRepository;
import com.example.jobfinder.service.CandidateScheduleService;

@Service
public class CandidateScheduleServiceImpl implements CandidateScheduleService {
    @Autowired
    private CandidateScheduleRepository candidateScheduleRepository;

    @Override
    public boolean update(long candidateId, List<ScheduleDTO> scheduleDTOs) {

        Queue<CandidateSchedule> oldCandidateSchedules =
                new LinkedList<>(candidateScheduleRepository.findAllByCandidate_Id(candidateId));

        if (scheduleDTOs == null || scheduleDTOs.isEmpty()) {
            candidateScheduleRepository.deleteAll(oldCandidateSchedules);
            return true;
        }

        for (ScheduleDTO newScheduleDTO : scheduleDTOs) {
            if (oldCandidateSchedules.isEmpty()) {
                CandidateSchedule newCandidateSchedule = new CandidateSchedule();

                Candidate candidate = new Candidate();
                candidate.setId(candidateId);
                Schedule schedule = new Schedule();
                schedule.setId(newScheduleDTO.getId());
                schedule.setName(newScheduleDTO.getName());

                newCandidateSchedule.setCandidate(candidate);
                newCandidateSchedule.setSchedule(schedule);
                candidateScheduleRepository.save(newCandidateSchedule);
            } else {
                CandidateSchedule candidateSchedule = oldCandidateSchedules.poll();
                Schedule newSchedule = new Schedule();
                newSchedule.setId(newScheduleDTO.getId());
                newSchedule.setName(newScheduleDTO.getName());
                candidateSchedule.setSchedule(newSchedule);
                candidateScheduleRepository.save(candidateSchedule);
            }
        }

        while (!oldCandidateSchedules.isEmpty()) {
            candidateScheduleRepository.delete(oldCandidateSchedules.poll());
        }

        return true;
    }

    @Override
    public List<CandidateSchedule> findAllByCandidate_Id(long id) {

        return candidateScheduleRepository.findAllByCandidate_Id(id);
    }
}
