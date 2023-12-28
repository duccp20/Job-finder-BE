package com.example.jobfinder.data.dto.response.candidate;

import com.example.jobfinder.constant.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApplicationDTONotShowJob {
	private int id;
	private int candidateId;
	private String candidateFirstName;
	private String candidateLastName;
	private String candidatePhoneNumber;
	private String candidateEmail;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.DATE_TIME_FORMAT)
	private Date createdDate;
	private String referenceLetter;
	private String CV;
}
