package com.example.jobfinder.data.dto.request.user;

import com.example.jobfinder.data.dto.request.StatusDTO;
import com.example.jobfinder.data.dto.request.role.RoleDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class UserDTO extends UserProfileDTO{
    private Long id;
    private RoleDTO roleDTO;
    private StatusDTO statusDTO;
}
