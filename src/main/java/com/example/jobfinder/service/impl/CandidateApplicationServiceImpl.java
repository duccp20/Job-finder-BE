package com.example.jobfinder.service.impl;

import com.example.jobfinder.data.dto.request.PaginationDTO;
import com.example.jobfinder.data.dto.request.candidate.CandidateApplicationDTO;
import com.example.jobfinder.data.dto.request.candidate.CandidateDTO;
import com.example.jobfinder.data.dto.request.job.JobDTO;
import com.example.jobfinder.data.dto.request.user.UserDTO;
import com.example.jobfinder.data.dto.response.candidate.ApplicationDTONotShowJob;
import com.example.jobfinder.data.entity.Candidate;
import com.example.jobfinder.data.entity.CandidateApplication;
import com.example.jobfinder.data.entity.User;
import com.example.jobfinder.data.mapper.CandidateApplicationMapper;
import com.example.jobfinder.data.repository.CandidateApplicationRepository;
import com.example.jobfinder.data.repository.CandidateRepository;
import com.example.jobfinder.data.repository.UserRepository;
import com.example.jobfinder.exception.InternalServerErrorException;
import com.example.jobfinder.exception.ResourceNotFoundException;
import com.example.jobfinder.service.*;
import com.example.jobfinder.utils.common.UpdateFile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;

@Service
public class CandidateApplicationServiceImpl implements CandidateApplicationService {
    @Autowired
    private FileService fileService;
    @Autowired
    private CandidateApplicationRepository candidateApplicationRepository;
    @Autowired
    private CandidateService candidateService;
    @Autowired
    private JobService jobService;
    @Autowired
    private CandidateApplicationMapper candidateApplicationMapper;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private UpdateFile updateFile;
    @Autowired
    ServletContext context;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CandidateRepository candidateRepository;
    public final static Logger LOGGER = LoggerFactory.getLogger("info");
    public final static int zeroValue = 0;


    @Override
    public PaginationDTO findAllByCandidateId(int no, int limit) {

        UserDTO userDTO = userService.getCurrentLoginUser();
        CandidateDTO candidate = candidateService.findByUserId(userDTO.getId());

        Page<CandidateApplicationDTO> applicationPages = this.candidateApplicationRepository
                .findAllByCandidateIdOrderByCreatedDateDesc(candidate.getId(), PageRequest.of(no, limit))
                .map(a -> this.candidateApplicationMapper.toDTO(a));

        if (applicationPages.getTotalElements() == zeroValue)
            throw new ResourceNotFoundException(Collections.singletonMap("message", "Job not found!"));

        return new PaginationDTO(applicationPages.getContent(), applicationPages.isFirst(), applicationPages.isLast(),
                applicationPages.getTotalPages(), applicationPages.getTotalElements(), applicationPages.getSize(),
                applicationPages.getNumber());
    }

    @Override
    public PaginationDTO findAllByJobId(long jobId, int no, int limit) {

        Page<ApplicationDTONotShowJob> applicationPages = this.candidateApplicationRepository
                .findAllByJobId(jobId, PageRequest.of(no, limit))
                .map(a -> this.candidateApplicationMapper.toDTONotShowJob(a));

        if (applicationPages.getTotalElements() == zeroValue)
            throw new ResourceNotFoundException(Collections.singletonMap("message", "Job not found!"));

        return new PaginationDTO(applicationPages.getContent(), applicationPages.isFirst(), applicationPages.isLast(),
                applicationPages.getTotalPages(), applicationPages.getTotalElements(), applicationPages.getSize(),
                applicationPages.getNumber());
    }


    @Override
    public boolean checkCandidateApplication(int idJob) {

        UserDTO user = userService.getCurrentLoginUser();
        Candidate candidateDTO = candidateRepository
                .findByUserId(user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException
                                (Collections.singletonMap("userID", user.getId())));

        return this.existsByJobIdAndCandidateId(idJob,
                candidateDTO.getId());
    }

    @Override
    public CandidateApplicationDTO create(CandidateApplicationDTO candidateApplicationDTO) {
        //Get cadidate from current login user
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("email", email))
        );
        CandidateDTO candidateDTO = candidateService.findByUserId(user.getId());

        // check apply
        if (this.existsByJobIdAndCandidateId(candidateApplicationDTO.getJobDTO().getId(),
                candidateDTO.getId())) {
            throw new InternalServerErrorException(Collections.singletonMap("message", "You have already applied for this job!"));
        }

        // check job apply
        JobDTO jobDTO = jobService.findById(candidateApplicationDTO.getJobDTO().getId());
        candidateApplicationDTO.setJobDTO(jobDTO);
        if (!jobService.isAppliable(jobDTO))
            throw new InternalServerErrorException("Job is not appliable");


        // set value candidate apply
        candidateApplicationDTO.setCandidateDTO(candidateDTO);
        candidateApplicationDTO.setEmail(candidateDTO.getUserDTO().getEmail());
        candidateApplicationDTO.setFullName(candidateDTO.getUserDTO().getLastName() + " " + candidateDTO.getUserDTO().getFirstName());
        candidateApplicationDTO.setPhone(candidateDTO.getUserDTO().getPhone());

        // map value to enetity to save
        CandidateApplication candidateApplication = candidateApplicationMapper.toEntity(candidateApplicationDTO);
        candidateApplication.setId(zeroValue);
        candidateApplication = candidateApplicationRepository.save(candidateApplication);
        CandidateApplicationDTO applicationDTO = candidateApplicationMapper.toDTO(candidateApplication);
        applicationDTO.setJobDTO(jobDTO);

        return applicationDTO;
    }

    @Override
    public CandidateApplicationDTO readJson(String value, MultipartFile fileCV) {

        CandidateApplicationDTO candidateApplicationDTO = new CandidateApplicationDTO();

        try {
            ObjectMapper ob = new ObjectMapper();
            candidateApplicationDTO = ob.readValue(value, CandidateApplicationDTO.class);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            throw new InternalServerErrorException(this.messageSource.getMessage("error.readJson", null, null));
        }

        if (fileCV != null) {// Set file Logo
            String fileCVName = fileService.uploadFile(fileCV);
            candidateApplicationDTO.setCV(fileCVName);
        }

        return candidateApplicationDTO;
    }

    @Override
    public boolean existsByJobIdAndCandidateId(long jobId, long candidateId) {

        return candidateApplicationRepository.existsByCandidateIdAndJobId(candidateId, jobId);
    }

    @Override
    public PaginationDTO findAll(int no, int limit) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public CandidateApplicationDTO findById(long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public CandidateApplication getById(long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    @Override
    public CandidateApplicationDTO update(long id, CandidateApplicationDTO candidateApplicationDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public boolean deleteById(long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

}
