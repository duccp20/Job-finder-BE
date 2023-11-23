package com.example.jobfinder.data.dto.response.user;

import lombok.Data;

@Data
public class ShowUserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private String roleName;
}