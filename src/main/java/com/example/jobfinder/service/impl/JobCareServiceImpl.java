package com.example.jobfinder.service.impl;

import com.example.jobfinder.data.dto.request.PaginationDTO;
import com.example.jobfinder.data.dto.request.candidate.CandidateDTO;
import com.example.jobfinder.data.dto.request.job.JobCareDTO;
import com.example.jobfinder.data.dto.request.job.JobDTO;
import com.example.jobfinder.data.dto.response.ResponseMessage;
import com.example.jobfinder.data.entity.Candidate;
import com.example.jobfinder.data.entity.JobCare;
import com.example.jobfinder.data.entity.User;
import com.example.jobfinder.data.mapper.JobCareMapper;
import com.example.jobfinder.data.repository.CandidateRepository;
import com.example.jobfinder.data.repository.JobCareRepository;
import com.example.jobfinder.data.repository.JobRepository;
import com.example.jobfinder.data.repository.UserRepository;
import com.example.jobfinder.exception.AccessDeniedException;
import com.example.jobfinder.exception.ResourceNotFoundException;
import com.example.jobfinder.service.CandidateService;
import com.example.jobfinder.service.JobCareService;
import com.example.jobfinder.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class JobCareServiceImpl implements JobCareService {
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private CandidateService candidateService;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private JobServiceImpl jobServiceImpl;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private JobCareMapper jobCareMapper;
    @Autowired
    private JobCareRepository jobCareRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    public final static int ActiveStatic = 1;
    public final static Logger LOGGER = LoggerFactory.getLogger("info");

    @Override
    public PaginationDTO findAllByCandidateId(long candidateId, int no, int limit) {

        Page<JobCareDTO> page = this.jobCareRepository
                .findAllByCandidateId((int) candidateId, PageRequest.of(no, limit))
                .map(item -> this.jobCareMapper.toDTO(item));

        return new PaginationDTO(page.getContent(), page.isFirst(), page.isLast(), page.getTotalPages(),
                page.getTotalElements(), page.getSize(), page.getNumber());

    }

    @Override
    public List<Integer> findJobSaveOfCandidateID() {

        Optional<Candidate> candidate = candidateRepository.findByUserId(userService.getCurrentUserId());
        List<Integer> listJobId = this.jobCareRepository.finJobSave(candidate.get().getId());
        if (listJobId.isEmpty())
            return null;

        return listJobId;
    }

    @Override
    public List<JobCareDTO> findAll() {

        return this.jobCareRepository.findAll().stream().map(item -> this.jobCareMapper.toDTO(item))
                .collect(Collectors.toList());

    }

    @Override
    public JobCareDTO findById(int id) {

        return this.jobCareMapper.toDTO(this.jobCareRepository.findById((long) id)
                .orElseThrow(() -> new ResourceNotFoundException(Collections.singletonMap("id", id))));

    }

    @Override
    public JobCareDTO findByCandidateIdAndJobId(int candidateId, int jobId) {

        return this.jobCareMapper.toDTO(this.jobCareRepository.findByCandidateIdAndJobId(candidateId, jobId)
                .orElseThrow(() -> new ResourceNotFoundException(Collections.singletonMap("id", jobId))));

    }

    @Override
    public ResponseMessage create(long idJob) {

        JobDTO jobDTO = jobServiceImpl.findById(idJob);
        CandidateDTO candidateDTO = candidateService.findByUserId(userService.getCurrentUserId());
        // verify that candidate and job in database

        if (jobDTO.getStatusDTO().getStatusId() != ActiveStatic)
            throw new IllegalArgumentException();
        JobCareDTO jobCareDTO = new JobCareDTO();
        jobCareDTO.setJobDTO(jobDTO);
        jobCareDTO.setCandidateDTO(candidateDTO);

        JobCare care = jobCareMapper.toEntity(jobCareDTO);
        jobCareRepository.save(care);

        return ResponseMessage.builder()
                .data(jobCareMapper.toDTO(care))
                .message("Create job care successfully")
                .build();
    }

    @Override
    public ResponseMessage deleteById(long idJobCare) {

        JobCareDTO jobCareDTO = findById((int) idJobCare);
        CandidateDTO candidateDTO = candidateService.findByUserId(userService.getCurrentUserId());
        Map<String, Object> errors = new HashMap<String, Object>();

        if (Objects.isNull(jobCareDTO)) {
            errors.put("idJobCare", idJobCare);
            throw new ResourceNotFoundException(errors);
        }

        if (Objects.equals(candidateDTO.getId(), jobCareDTO.getCandidateDTO().getId())) {
            this.jobCareRepository.delete(this.jobCareRepository.findById((long) idJobCare)
                    .orElseThrow(() -> new ResourceNotFoundException(Collections.singletonMap("id", idJobCare))));
            return ResponseMessage.builder()
                    .message("Delete job care successfully")
                    .data(jobCareDTO)
                    .build();
        } else {
            throw new AccessDeniedException();
        }
    }

    @Override
    public boolean checkCandidateApplication(int idJob) {

        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userRepository.findByEmail(email).orElseThrow();
        CandidateDTO candidateDTO = candidateService.findByUserId(user.getId());

        return this.existsByJobIdAndCandidateId(idJob, candidateDTO.getId());
    }

    @Override
    public boolean existsByJobIdAndCandidateId(long jobId, long candidateId) {
        return jobCareRepository.existsByCandidateIdAndJobId(candidateId, jobId);
    }

    @Override
    public PaginationDTO findAllByJobId(int jobId, int no, int limit) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllByJobId'");
    }
}