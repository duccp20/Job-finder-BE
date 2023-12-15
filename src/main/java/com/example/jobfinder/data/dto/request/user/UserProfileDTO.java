package com.example.jobfinder.data.dto.request.user;


import java.io.Serializable;
import java.util.Date;


import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserProfileDTO implements Serializable {

    @Email(message = "Email không đúng định dạng")
    @NotEmpty(message = "Email không được phép để trống")
    private String email;
    private String firstName;
    private String lastName;
    private Boolean gender;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date birthDay;
    private String phone;
    private String avatar;
    private String location;
    private boolean mailReceive;

}
