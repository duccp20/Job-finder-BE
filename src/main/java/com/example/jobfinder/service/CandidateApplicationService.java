package com.example.jobfinder.service;

import com.example.jobfinder.data.dto.request.PaginationDTO;
import com.example.jobfinder.data.dto.request.candidate.CandidateApplicationDTO;
import com.example.jobfinder.data.entity.CandidateApplication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CandidateApplicationService {

	PaginationDTO findAll(int no, int limit);

	PaginationDTO findAllByCandidateId(int no, int limit);

	PaginationDTO findAllByJobId(long jobId, int no, int limit);

	CandidateApplicationDTO findById(long id);

	CandidateApplication getById(long id);

    CandidateApplicationDTO readJson(String value, MultipartFile fileCV);

    boolean existsByJobIdAndCandidateId(long jobId, long candidateId);

	boolean checkCandidateApplication(int idJob);

	CandidateApplicationDTO create(CandidateApplicationDTO candidateApplicationDTO, MultipartFile fileCV) throws IOException;

	CandidateApplicationDTO update(long id, CandidateApplicationDTO candidateApplicationDTO);

	boolean deleteById(long id);

}
