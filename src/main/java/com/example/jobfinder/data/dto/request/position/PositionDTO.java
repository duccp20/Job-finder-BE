package com.example.jobfinder.data.dto.request.position;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PositionDTO implements Serializable {
	private int id;
	private String name;
	
}
