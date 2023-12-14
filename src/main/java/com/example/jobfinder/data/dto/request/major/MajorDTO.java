package com.example.jobfinder.data.dto.request.major;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class MajorDTO implements Serializable {
	private int id;
	private String name;
}
