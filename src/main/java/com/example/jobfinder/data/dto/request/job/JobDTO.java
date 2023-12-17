package com.example.jobfinder.data.dto.request.job;

import com.example.jobfinder.data.dto.request.StatusDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class JobDTO extends JobCreationDTO {
	private long id;
	private StatusDTO statusDTO;
	private int numOfApplication;


}
