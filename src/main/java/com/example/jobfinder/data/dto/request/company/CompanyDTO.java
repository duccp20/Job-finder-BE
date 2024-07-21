package com.example.jobfinder.data.dto.request.company;

import java.io.Serializable;
import java.util.Date;

import com.example.jobfinder.data.dto.request.StatusDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CompanyDTO implements Serializable {
    private Long id;
    private String name;
    private String logo;
    private String description;
    private String website;
    private String email;
    private String phone;
    private String tax;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date createdDate;

    private String location;
    private String personnelSize;
    private StatusDTO statusDTO;
}
