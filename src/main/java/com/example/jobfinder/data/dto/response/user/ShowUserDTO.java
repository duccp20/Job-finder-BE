package com.example.jobfinder.data.dto.response.user;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShowUserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private boolean gender;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date birthDay;

    private String address;
    private String email;
    private String phone;
    private String avatar;
    private String role;
}
