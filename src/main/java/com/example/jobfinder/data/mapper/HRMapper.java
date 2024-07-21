package com.example.jobfinder.data.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import com.example.jobfinder.data.dto.request.hr.HRCreationDTO;
import com.example.jobfinder.data.dto.request.hr.HRDTO;
import com.example.jobfinder.data.dto.request.hr.HRProfileDTO;
import com.example.jobfinder.data.entity.HR;

@Mapper(
        componentModel = "spring",
        uses = {UserMapper.class, CompanyMapper.class})
@Component
public interface HRMapper {

    @Mapping(source = "userCreationDTO", target = "user")
    @Mapping(source = "hrOtherInfoDTO.position", target = "position")
    @Mapping(source = "hrOtherInfoDTO.companyDTO", target = "company")
    HR toEntity(HRCreationDTO hrCreationDTO);

    @Mapping(source = "userProfileDTO", target = "user")
    @Mapping(source = "position", target = "position")
    // @Mapping(source = "hrOtherInfoDTO.companyDTO", target = "company")
    HR toEntity(HRProfileDTO hrProfileDTO);

    @Mapping(source = "user", target = "userDTO")
    @Mapping(source = "position", target = "hrOtherInfoDTO.position")
    @Mapping(source = "company", target = "hrOtherInfoDTO.companyDTO")
    HRDTO toDTO(HR hr);
}
