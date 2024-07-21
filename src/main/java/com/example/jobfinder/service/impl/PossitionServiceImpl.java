package com.example.jobfinder.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jobfinder.data.dto.request.position.PositionDTO;
import com.example.jobfinder.data.dto.response.ApiResponse;
import com.example.jobfinder.data.entity.Position;
import com.example.jobfinder.data.mapper.PositionMapper;
import com.example.jobfinder.data.repository.PositionRepository;
import com.example.jobfinder.exception.InternalServerErrorException;
import com.example.jobfinder.exception.ResourceNotFoundException;
import com.example.jobfinder.service.PositionService;

@Service
public class PossitionServiceImpl implements PositionService {

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private PositionMapper positionMapper;
    //    @Autowired
    //    private HRRepository hrRepository;
    //    @Autowired
    //    private JobRepository jobRepository;
    //    @Autowired
    //    private CandidateApplicationRepository candidateApplicationRepository;

    @Override
    public PositionDTO findById(Integer id) {
        return null;
    }

    @Override
    public List<PositionDTO> findAll() {
        return this.positionRepository.findAll().stream()
                .map(item -> this.positionMapper.toDTO(item))
                .collect(Collectors.toList());
    }

    @Override
    public ApiResponse create(PositionDTO positionDTO) {
        Position position = positionMapper.toEntity(positionDTO);
        if (positionRepository.existsByName(position.getName()))
            throw new InternalServerErrorException(String.format("Exists major named %s", position.getName()));

        return ApiResponse.builder()
                .httpCode(HttpServletResponse.SC_CREATED)
                .message("Created successfully")
                .data(positionMapper.toDTO(positionRepository.save(position)))
                .build();
    }

    @Override
    public ApiResponse deleteById(Integer id) {
        this.positionRepository.delete(this.positionRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Collections.singletonMap("id", id))));

        return ApiResponse.builder()
                .httpCode(HttpServletResponse.SC_OK)
                .message("Deleted successfully")
                .build();
    }

    //    @Override
    //    public Map<Position, int[]> statisticsPositionTheNumberOfPostsAndJoins() {
    //        String username = SecurityContextHolder.getContext().getAuthentication().getName();
    //        HR hr = this.hrRepository.findByUsername(username).orElseThrow(
    //                () -> new AccessDeniedException("FORBIDDEN"));
    //        Map<Position, int[]> counts = new HashMap<>();
    //
    //        List<Position> positions = positionRepository.findAll();
    //        for (int i = 0; i < positions.size(); i++) {
    //            counts.put(positions.get(i), new int[2]);
    //        }
    //
    //        for (Object[] result : jobRepository.countJobsByMonth(hr.getCompany().getId())) {
    //            int position = (int) result[0];
    //            int jobCount = ((Number) result[1]).intValue();
    //
    //            if (counts.containsKey(position)) {
    //                counts.get(position)[0] = jobCount;
    //            }
    //        }
    //
    //        for (Object[] result : candidateApplicationRepository.countApplicationsByMonth(hr.getCompany().getId())) {
    //            int position = (int) result[0];
    //            int applicationCount = ((Number) result[1]).intValue();
    //
    //            if (counts.containsKey(position)) {
    //                counts.get(position)[1] = applicationCount;
    //            }
    //        }
    //
    //        return counts;
    //    }
}
