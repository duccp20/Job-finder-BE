package com.example.jobfinder.data.mapper;

import com.example.jobfinder.data.dto.request.StatusDTO;
import com.example.jobfinder.data.entity.Status;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface StatusMapper {

    Status toEntity(StatusDTO statusDTO);

    StatusDTO toDTO(Status status);
}