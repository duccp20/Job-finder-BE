package com.example.jobfinder.data.mapper;

import com.example.jobfinder.data.dto.request.position.PositionDTO;
import com.example.jobfinder.data.entity.CandidatePosition;
import com.example.jobfinder.data.entity.JobPosition;
import com.example.jobfinder.data.entity.Position;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface PositionMapper {

    Position toEntity(PositionDTO positionDTO);

    PositionDTO toDTO(Position position);

    @Mapping(source = "position.id", target = "id")
    @Mapping(source = "position.name", target = "name")
    PositionDTO toPositionDTO(CandidatePosition candidatePosition);

    @Mapping(source = "position.id", target = "id")
    @Mapping(source = "position.name", target = "name")
    PositionDTO jobPositiontoPositionDto(JobPosition jobPosition);

//    @Mapping(source = "position.id", target = "id")
//    @Mapping(source = "position.name", target = "name")
//    PositionDTO toPositionDto(InternshipPosition internshipPosition);

}

