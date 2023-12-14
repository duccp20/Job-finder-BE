package com.example.jobfinder.data.dto.request.schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ScheduleDTO implements Serializable {
	private int id;
	private String name;
}
