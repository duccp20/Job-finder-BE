package com.example.jobfinder.data.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ApiResponse<T> {
    private int httpCode;
    private String message;
    private T data;
    private String path;
}
