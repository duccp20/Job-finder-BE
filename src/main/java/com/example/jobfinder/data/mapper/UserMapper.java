package com.example.jobfinder.data.mapper;

import com.example.jobfinder.data.dto.user.UserCreationDTO;
import com.example.jobfinder.data.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring", uses = { RoleMapper.class, StatusMapper.class })
@Component
public interface UserMapper {


    @Mapping(target = "tokenActive", ignore = true)
    @Mapping(target = "passwordForgotToken", ignore = true)
    @Mapping(target = "mailReceive", ignore = true)
    @Mapping(target = "location", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "gender", ignore = true)
    @Mapping(target = "birthDay", ignore = true)
    @Mapping(target = "avatar", ignore = true)
    @Mapping(target = "authProvider", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "role", ignore = true)
    User toEntity(UserCreationDTO userProfileDTO);
    @Mapping(target = "role", ignore = true)
    UserCreationDTO toDTO(User user);

}