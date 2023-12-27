package com.example.jobfinder.service.impl;

import com.example.jobfinder.data.dto.request.schedule.ScheduleDTO;
import com.example.jobfinder.data.dto.response.ResponseMessage;
import com.example.jobfinder.data.entity.Schedule;
import com.example.jobfinder.data.mapper.ScheduleMapper;
import com.example.jobfinder.data.repository.ScheduleRepository;
import com.example.jobfinder.exception.InternalServerErrorException;
import com.example.jobfinder.exception.ResourceNotFoundException;
import com.example.jobfinder.service.ScheduleService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private ScheduleMapper scheduleMapper;



    @Override
    public ScheduleDTO findById(Integer id) {
        return null;
    }

    @Override
    public List<ScheduleDTO> findAll() {
        return this.scheduleRepository.findAll().stream().map(item -> this.scheduleMapper.toDTO(item))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseMessage create(ScheduleDTO scheduleDTO) {
        Schedule schedule = scheduleMapper.toEntity(scheduleDTO);
        if (scheduleRepository.existsByName(schedule.getName()))
            throw new InternalServerErrorException(
                    String.format("Exists major named %s", schedule.getName()));

    return ResponseMessage.builder()
            .httpCode(HttpServletResponse.SC_CREATED)
            .message("Created successfully")
            .data(scheduleMapper.toDTO(scheduleRepository.save(schedule)))
            .build();
    }

    @Override
    public ResponseMessage deleteById(Integer id) {
        this.scheduleRepository.delete(this.scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Collections.singletonMap("id", id))));

        return ResponseMessage.builder()
                .httpCode(HttpServletResponse.SC_OK)
                .message("Deleted successfully")
                .build();
    }
}
