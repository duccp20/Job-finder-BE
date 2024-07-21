package com.example.jobfinder.data.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import com.example.jobfinder.data.dto.request.role.RoleDTO;
import com.example.jobfinder.data.entity.Role;

@Mapper(componentModel = "spring")
@Component
public interface RoleMapper {

    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    Role toEntity(RoleDTO roleDTO);

    RoleDTO toDTO(Role role);
}
