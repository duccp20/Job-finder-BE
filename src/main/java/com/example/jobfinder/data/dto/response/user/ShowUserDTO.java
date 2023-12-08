package com.example.jobfinder.data.dto.response.user;

import lombok.Data;

@Data
public class ShowUserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String avatar;
    private String role;
}