package com.example.jobfinder.data.dto.request.job;

import java.io.Serializable;

import com.example.jobfinder.data.dto.request.position.PositionDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JobPositionDTO implements Serializable {
    private JobDTO jobDTO;

    private PositionDTO positionDTO;
}
