package com.example.jobfinder.data.dto.request.job;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.example.jobfinder.data.dto.request.company.CompanyDTO;
import com.example.jobfinder.data.dto.request.major.MajorDTO;
import com.example.jobfinder.data.dto.request.position.PositionDTO;
import com.example.jobfinder.data.dto.request.schedule.ScheduleDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JobCreationDTO implements Serializable {

    private CompanyDTO companyDTO;
    private String name;
    private List<PositionDTO> positionDTOs;
    private List<MajorDTO> majorDTOs;
    private List<ScheduleDTO> scheduleDTOs;
    private int amount;

    private Date startDate;

    private Date endDate;
    private long salaryMin;
    private long salaryMax;
    private String province;
    private String location;
    private String description;
    private String requirement;
    private String otherInfo;
}
