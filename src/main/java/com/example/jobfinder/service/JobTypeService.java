package com.example.jobfinder.service;

import java.util.List;

import com.example.jobfinder.data.dto.request.schedule.ScheduleDTO;
import com.example.jobfinder.data.entity.Schedule;

public interface JobTypeService {

    boolean deleteById(Integer id);

    boolean existsById(Integer id);

    ScheduleDTO findById(Integer id);

    List<ScheduleDTO> findAll();

    ScheduleDTO update(int id, ScheduleDTO jobTypeDTO);

    Schedule getById(Integer id);

    ScheduleDTO create(ScheduleDTO jobTypeDTO);

    boolean existsByName(String name);
}
