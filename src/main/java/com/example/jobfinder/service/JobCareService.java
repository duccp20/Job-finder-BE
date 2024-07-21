package com.example.jobfinder.service;

import java.util.List;

import com.example.jobfinder.data.dto.request.PaginationDTO;
import com.example.jobfinder.data.dto.request.job.JobCareDTO;
import com.example.jobfinder.data.dto.response.ApiResponse;

public interface JobCareService {

    PaginationDTO findAllByCandidateId(long candidateId, int no, int limit);

    List<Integer> findJobSaveOfCandidateID();

    PaginationDTO findAllByJobId(int jobId, int no, int limit);

    List<JobCareDTO> findAll();

    JobCareDTO findById(int id);

    JobCareDTO findByCandidateIdAndJobId(int candidateId, int jobId);

    ApiResponse create(long idJob);

    ApiResponse deleteById(long idJobCare);

    ApiResponse deleteByJobId(long jobId);

    boolean checkCandidateApplication(int idJob);

    boolean existsByJobIdAndCandidateId(long jobId, long candidateId);

    //    JobCareDTO update(int id, JobCareDTO jobCareDTO);
}
