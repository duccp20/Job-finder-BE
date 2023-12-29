package com.example.jobfinder.data.dto.request.job;

import com.example.jobfinder.data.dto.request.StatusDTO;
import com.example.jobfinder.data.dto.request.company.CompanyDTO;
import com.example.jobfinder.data.dto.request.major.MajorDTO;
import com.example.jobfinder.data.dto.request.position.PositionDTO;
import com.example.jobfinder.data.dto.request.schedule.ScheduleDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@AllArgsConstructor
@Data
@NoArgsConstructor
public class JobShowDTO {
    private int id;
    private String name;
    private CompanyDTO companyDTO;
    private List<PositionDTO> positionDTOs;
    private List<MajorDTO> majorDTOs;
    private List<ScheduleDTO> scheduleDTOs;
    private int amount;
    private Date startDate;
    private Date endDate;
    private long salaryMin;
    private long salaryMax;
    private String description;
    private String requirement;
    private String otherInfo;
    private String province;
    private String location;
    private StatusDTO statusDTO;
}
