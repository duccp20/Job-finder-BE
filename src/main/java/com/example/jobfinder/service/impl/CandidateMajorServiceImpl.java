package com.example.jobfinder.service.impl;

import com.example.jobfinder.data.dto.request.major.MajorDTO;
import com.example.jobfinder.data.entity.Candidate;
import com.example.jobfinder.data.entity.CandidateMajor;
import com.example.jobfinder.data.entity.Major;
import com.example.jobfinder.data.repository.CandidateMajorRepository;
import com.example.jobfinder.data.repository.CandidateRepository;
import com.example.jobfinder.data.repository.MajorRepository;
import com.example.jobfinder.exception.ResourceNotFoundException;
import com.example.jobfinder.service.CandidateMajorService;
import com.example.jobfinder.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CandidateMajorServiceImpl implements CandidateMajorService {
    @Autowired
    private CandidateMajorRepository candidateMajorRepository;

    @Autowired
    private MajorRepository majorRepository;

    @Autowired
    private CandidateRepository candidateRepository;



    @Override
    @Transactional
    public boolean update(long candidateId, List<MajorDTO> majorDTOs) {
        List<CandidateMajor> existingCandidateMajors = new ArrayList<>(
                candidateMajorRepository.findAllByCandidate_Id(candidateId));
        List<CandidateMajor> candidateMajorsToSave = new ArrayList<>();

        if (majorDTOs == null || majorDTOs.isEmpty()) {
            candidateMajorRepository.deleteAll(existingCandidateMajors);
            return true;
        }

        for (MajorDTO newMajorDTO : majorDTOs) {
            CandidateMajor candidateMajor = existingCandidateMajors.stream()
                    .filter(cm -> cm.getMajor().getId() == newMajorDTO.getId())
                    .findFirst()
                    .orElse(new CandidateMajor());

            if (candidateMajor.getId() == 0) {
                Candidate candidate = candidateRepository.findById(candidateId)
                        .orElseThrow(() -> new ResourceNotFoundException(Collections.singletonMap("id", candidateId)));
                Major major = majorRepository.findById(newMajorDTO.getId())
                        .orElseThrow(() -> new ResourceNotFoundException(Collections.singletonMap("id", newMajorDTO.getId())));
                candidateMajor.setCandidate(candidate);
                candidateMajor.setMajor(major);
            }

            candidateMajorsToSave.add(candidateMajor);
            existingCandidateMajors.remove(candidateMajor);
        }

        // Save all the candidate majors at once
        candidateMajorRepository.saveAll(candidateMajorsToSave);

        // Delete any remaining old candidate majors
        candidateMajorRepository.deleteAll(existingCandidateMajors);

        return true;
    }

    @Override
    public List<CandidateMajor> findAllByCandidate_Id(long candidateId) {
        return  candidateMajorRepository.findAllByCandidate_Id(candidateId);
    }
}
