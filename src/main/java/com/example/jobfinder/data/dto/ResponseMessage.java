package com.example.jobfinder.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseMessage {
    private String message;
    private Map<String, Object> data;

}
