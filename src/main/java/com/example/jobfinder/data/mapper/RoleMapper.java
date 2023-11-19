package com.example.jobfinder.data.mapper;


import com.example.jobfinder.data.dto.RoleDTO;
import com.example.jobfinder.data.entity.Role;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface RoleMapper {

    Role toEntity(RoleDTO roleDTO);

    RoleDTO toDTO(Role role);

}
