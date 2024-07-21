package com.example.jobfinder.data.dto.response;

import lombok.*;

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
