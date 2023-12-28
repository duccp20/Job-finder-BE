package com.example.jobfinder.data.dto.request.candidate;

import com.example.jobfinder.constant.Constant;
import com.example.jobfinder.data.dto.request.job.JobDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CandidateApplicationDTO implements Serializable {
	private Long id;
	private JobDTO jobDTO;
	private CandidateDTO candidateDTO;
	@JsonFormat(pattern = Constant.DATE_TIME_FORMAT)
	private Date appliedDate;
	private String CV;
	private String referenceLetter;
	private String email;
	private String fullName;
	private String phone;
}
