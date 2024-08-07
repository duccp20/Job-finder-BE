package com.example.jobfinder.data.dto.request.job;

import java.io.Serializable;

import com.example.jobfinder.data.dto.request.schedule.ScheduleDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JobScheduleDTO implements Serializable {
    private JobDTO jobDTO;

    private ScheduleDTO scheduleDTO;
}
