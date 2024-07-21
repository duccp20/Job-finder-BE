package com.example.jobfinder.data.dto.request.schedule;

import java.io.Serializable;

import com.example.jobfinder.data.dto.request.job.JobDTO;

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
