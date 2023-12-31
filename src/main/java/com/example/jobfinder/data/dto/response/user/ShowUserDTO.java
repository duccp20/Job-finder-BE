package com.example.jobfinder.data.dto.response.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

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