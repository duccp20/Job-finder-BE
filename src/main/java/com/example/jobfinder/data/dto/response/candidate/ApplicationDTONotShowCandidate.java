package com.example.jobfinder.data.dto.response.candidate;

import java.util.Date;

import com.example.jobfinder.constant.Constant;
import com.example.jobfinder.data.dto.request.job.JobDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApplicationDTONotShowCandidate {
    private int id;
    private JobDTO jobDTO;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.DATE_TIME_FORMAT)
    private Date createdDate;

    private String referenceLetter;
    private String CV;
}
