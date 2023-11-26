package com.example.jobfinder.data.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class ErrorMessageDTO {
    private String message;
    private Object errors;
    private String path;
}
