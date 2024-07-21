package com.example.jobfinder.data.dto.request.job;

import java.util.Date;
import java.util.List;

import com.example.jobfinder.constant.Constant;
import com.example.jobfinder.data.dto.request.StatusDTO;
import com.example.jobfinder.data.dto.request.company.CompanyDTO;
import com.example.jobfinder.data.dto.request.major.MajorDTO;
import com.example.jobfinder.data.dto.request.position.PositionDTO;
import com.example.jobfinder.data.dto.request.schedule.ScheduleDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String createBy;

    @JsonFormat(pattern = Constant.DATE_TIME_FORMAT, timezone = "Asia/Ho_Chi_Minh")
    private Date lastModifiedDate;

    private int amount;

    @JsonFormat(pattern = Constant.DATE_TIME_FORMAT, timezone = "Asia/Ho_Chi_Minh")
    private Date startDate;

    @JsonFormat(pattern = Constant.DATE_TIME_FORMAT, timezone = "Asia/Ho_Chi_Minh")
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
