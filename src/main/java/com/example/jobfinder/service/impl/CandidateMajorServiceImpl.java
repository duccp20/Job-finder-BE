package com.example.jobfinder.service.impl;

import com.example.jobfinder.data.dto.request.major.MajorDTO;
import com.example.jobfinder.data.entity.Candidate;
import com.example.jobfinder.data.entity.CandidateMajor;
import com.example.jobfinder.data.entity.Major;
import com.example.jobfinder.data.repository.CandidateMajorRepository;
import com.example.jobfinder.service.CandidateMajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Service
public class CandidateMajorServiceImpl implements CandidateMajorService {
    @Autowired
    private CandidateMajorRepository candidateMajorRepository;

    @Override
    public boolean update(long candidateId, List<MajorDTO> majorDTOs) {

        Queue<CandidateMajor> oldCandidateMajors = new LinkedList<>(
                candidateMajorRepository.findAllByCandidate_Id(candidateId));

        if (majorDTOs == null || majorDTOs.isEmpty()) {
            candidateMajorRepository.deleteAll(oldCandidateMajors);
            return true;
        }

        for (MajorDTO newMajorDTO : majorDTOs) {
            if (oldCandidateMajors.isEmpty()) {
                CandidateMajor newCandidateMajor = new CandidateMajor();

                Candidate candidate = new Candidate();
                candidate.setId(candidateId);
                Major major = new Major();
                major.setId(newMajorDTO.getId());

                newCandidateMajor.setCandidate(candidate);
                newCandidateMajor.setMajor(major);
                candidateMajorRepository.save(newCandidateMajor);
            } else {
                CandidateMajor candidateMajor = oldCandidateMajors.poll();
                Major newMajor = new Major();
                newMajor.setId(newMajorDTO.getId());
                newMajor.setName(newMajorDTO.getName());
                candidateMajor.setMajor(newMajor);
                candidateMajorRepository.save(candidateMajor);
            }
        }

        while (!oldCandidateMajors.isEmpty()) {
            candidateMajorRepository.delete(oldCandidateMajors.poll());
        }

        return true;
    }

}
