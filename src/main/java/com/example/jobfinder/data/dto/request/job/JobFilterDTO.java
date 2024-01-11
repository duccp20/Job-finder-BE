package com.example.jobfinder.data.dto.request.job;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class JobFilterDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private List<String> positionIds;
    private List<String> scheduleIds;
    private List<String> majorIds;
    private String provinceName;
}
