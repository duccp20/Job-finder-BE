package com.example.jobfinder.data.dto.response;

import lombok.*;

import java.util.Map;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ResponseMessage {
    private int httpCode;
    private String message;
    private Object data;
    private String path;

}
