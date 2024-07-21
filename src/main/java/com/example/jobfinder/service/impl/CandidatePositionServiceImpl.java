package com.example.jobfinder.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jobfinder.data.dto.request.position.PositionDTO;
import com.example.jobfinder.data.entity.Candidate;
import com.example.jobfinder.data.entity.CandidatePosition;
import com.example.jobfinder.data.entity.Position;
import com.example.jobfinder.data.repository.CandidatePositionRepository;
import com.example.jobfinder.service.CandidatePositionService;

@Service
public class CandidatePositionServiceImpl implements CandidatePositionService {
    @Autowired
    private CandidatePositionRepository candidatePositionRepository;

    @Override
    public boolean update(long candidateId, List<PositionDTO> positionDTOs) {

        Queue<CandidatePosition> oldCandidatePositions =
                new LinkedList<>(candidatePositionRepository.findAllByCandidate_Id(candidateId));

        if (positionDTOs == null || positionDTOs.isEmpty()) {
            candidatePositionRepository.deleteAll(oldCandidatePositions);
            return true;
        }

        for (PositionDTO newPositionDTO : positionDTOs) {
            // if old empty and newPositionDTO exist -> create
            if (oldCandidatePositions.isEmpty()) {
                CandidatePosition newCandidatePosition = new CandidatePosition();

                Candidate candidate = new Candidate();
                candidate.setId(candidateId);
                Position position = new Position();
                position.setId(newPositionDTO.getId());
                position.setName(newPositionDTO.getName());

                newCandidatePosition.setCandidate(candidate);
                newCandidatePosition.setPosition(position);
                candidatePositionRepository.save(newCandidatePosition);
            } else { // if old not empty and newPositionDTO exist -> update
                CandidatePosition candidatePosition = oldCandidatePositions.poll();
                Position newPosition = new Position();
                newPosition.setId(newPositionDTO.getId());
                newPosition.setName(newPositionDTO.getName());
                candidatePosition.setPosition(newPosition);
                candidatePositionRepository.save(candidatePosition);
            }
        }

        while (!oldCandidatePositions.isEmpty()) {
            candidatePositionRepository.delete(oldCandidatePositions.poll());
        }

        return true;
    }

    @Override
    public List<CandidatePosition> findAllByCandidate_Id(long candidateId) {
        return candidatePositionRepository.findAllByCandidate_Id(candidateId);
    }
}
