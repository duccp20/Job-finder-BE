package com.example.jobfinder.data.dto.request.position;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PositionDTO implements Serializable {
    private int id;
    private String name;
}
