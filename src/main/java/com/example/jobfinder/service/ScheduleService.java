package com.example.jobfinder.service;

import java.util.List;

import com.example.jobfinder.data.dto.request.schedule.ScheduleDTO;
import com.example.jobfinder.data.dto.response.ApiResponse;

public interface ScheduleService {

    ScheduleDTO findById(Integer id);

    List<ScheduleDTO> findAll();

    ApiResponse create(ScheduleDTO scheduleDTO);

    ApiResponse deleteById(Integer id);
}
