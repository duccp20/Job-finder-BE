package com.example.jobfinder.data.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseMessage {
    private int httpCode;
    private String message;
    private Object data;
    private String path;

}
