package com.example.jobfinder.data.dto.request.mail;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmailRequest {

    @NotNull
    @NotEmpty
    private String email;
}
