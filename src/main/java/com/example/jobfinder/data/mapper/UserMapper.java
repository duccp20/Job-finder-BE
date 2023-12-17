package com.example.jobfinder.data.mapper;

import com.example.jobfinder.data.dto.request.user.UserCreationDTO;
import com.example.jobfinder.data.dto.request.user.UserDTO;
import com.example.jobfinder.data.dto.request.user.UserProfileDTO;
import com.example.jobfinder.data.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = { RoleMapper.class, StatusMapper.class })
@Component
public interface UserMapper {

	@Mapping(ignore = true, target = "password")
	@Mapping(constant = "true", target = "mailReceive")
	@Mapping(ignore = true, target = "role")
	@Mapping(ignore = true, target = "status")
	User toEntity(UserCreationDTO creationDTO);

	@Mapping(ignore = true, target = "role")
	@Mapping(ignore = true, target = "status")
	@Mapping(source = "location", target = "address")
	User toEntity(UserProfileDTO userProfileDTO);

	@Mapping(source = "role", target = "roleDTO")
	@Mapping(source = "status", target = "statusDTO")
	@Mapping(source = "address", target = "location")
	UserDTO toDTO(User user);

//	@Mapping(source = "roleDTO", target = "role")
//	@Mapping(source = "statusDTO", target = "status")
//	User toDTO(UserDTO userDTO);

	@Mapping(source = "roleDTO", target = "role")
	@Mapping(source = "statusDTO", target = "status")
	User toEntity(UserDTO userDTO);


}
