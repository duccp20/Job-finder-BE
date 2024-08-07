package com.example.jobfinder.data.dto.request.hr;

import java.io.Serializable;

import jakarta.validation.constraints.NotNull;

import com.example.jobfinder.data.dto.request.company.CompanyDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class HROtherInfoDTO implements Serializable {
    @NotNull(message = "The hr's position must not be null")
    private String position;

    @NotNull(message = "The hr's company must not be null")
    private CompanyDTO companyDTO;
}
