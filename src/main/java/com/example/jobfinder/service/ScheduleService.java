package com.example.jobfinder.service;

import java.util.List;

import com.example.jobfinder.data.dto.request.schedule.ScheduleDTO;
import com.example.jobfinder.data.dto.response.ResponseMessage;

public interface ScheduleService {

    ScheduleDTO findById(Integer id);

    List<ScheduleDTO> findAll();

    ResponseMessage create(ScheduleDTO scheduleDTO);

    ResponseMessage deleteById(Integer id);
}
