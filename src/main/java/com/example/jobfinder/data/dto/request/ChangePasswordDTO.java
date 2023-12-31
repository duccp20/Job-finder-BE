package com.example.jobfinder.data.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChangePasswordDTO implements Serializable {

	@NotNull(message = "old password is required")
	private String oldPassword;
	@NotNull(message = "new password is required")
	private String newPassword;
}
