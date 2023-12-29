package com.example.jobfinder.data.dto.response.job;

import com.example.jobfinder.data.dto.request.job.JobShowDTO;
import com.example.jobfinder.data.dto.request.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
@NoArgsConstructor
public class JobShowDTOv2 extends JobShowDTO {

    private Long createdBy;

}
