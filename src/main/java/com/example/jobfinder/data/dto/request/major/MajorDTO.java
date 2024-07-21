package com.example.jobfinder.data.dto.request.major;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MajorDTO implements Serializable {
    private int id;
    private String name;
}
