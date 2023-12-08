package com.example.jobfinder.data.dto.request.user;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResetPasswordByToken {
    @NotNull(message = "{error.token}")
    private String token;
    @NotNull(message = "{error.userNewPassword}")
    private String newPassword;
}
