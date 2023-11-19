package com.example.jobfinder.data.dto.user;

import lombok.Data;

@Data
public class UserCreationDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private int role;

}
