package com.example.jobfinder.data.mapper;

import com.example.jobfinder.data.dto.request.company.CompanyDTO;
import com.example.jobfinder.data.entity.Company;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = { StatusMapper.class})
@Component
public interface CompanyMapper {
    @Mapping(source = "statusDTO", target = "status")
    Company toEntity(CompanyDTO companyDTO);

    @Mapping(source = "status", target = "statusDTO")
    CompanyDTO toDTO(Company company);

}
