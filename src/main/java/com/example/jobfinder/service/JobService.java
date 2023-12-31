package com.example.jobfinder.service;

import com.example.jobfinder.data.dto.request.PaginationDTO;
import com.example.jobfinder.data.dto.request.job.JobCreationDTO;
import com.example.jobfinder.data.dto.request.job.JobDTO;
import com.example.jobfinder.data.dto.request.job.JobFilterDTO;
import com.example.jobfinder.data.dto.response.ResponseMessage;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

public interface JobService {

//    Long recruitmentNews (int month);

    PaginationDTO findAllDisableByCompanyIdShowForHr( int no, int limit);
    JobDTO findById(long id);

    @Transactional
    JobDTO create(JobCreationDTO jobCreationDTO);

    @Transactional
    PaginationDTO findAllActive(int no, int limit);

    JobDTO update(long id, JobDTO jobDTO);
    JobDTO replicate(long id, JobDTO jobDTO);

    PaginationDTO findAllActiveByCompanyIdShowForHr(int no, int limit);

    PaginationDTO findAllByCompanyIdShowForHr(int no, int limit);

    long countAllActiveByCompanyIdShowForHr();


    long countAllDisableByCompanyIdShowForHr();

    long countAllByCompanyId();

//    Long countByCreatedDate(LocalDateTime from, LocalDateTime to);

    PaginationDTO findAllActiveByCompanyId(long companyId, int no, int limit);

//    List<Object[]> getNewStatistics();

    boolean isAppliable(JobDTO jobDTO);

    PaginationDTO filterJob(JobFilterDTO jobFilterDTO, int no, int limit);

    Map<YearMonth, int[]> countByMonth();

    Map<Integer, int[]> countByYear();

    @Transactional
    ResponseMessage delete(long id);

    @Transactional
    ResponseMessage disable(long id);
}
