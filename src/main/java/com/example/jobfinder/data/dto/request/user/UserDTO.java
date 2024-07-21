package com.example.jobfinder.data.dto.request.user;

import com.example.jobfinder.data.dto.request.StatusDTO;
import com.example.jobfinder.data.dto.request.role.RoleDTO;

import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class UserDTO extends UserProfileDTO {
    private Long id;
    private RoleDTO roleDTO;
    private StatusDTO statusDTO;
}
