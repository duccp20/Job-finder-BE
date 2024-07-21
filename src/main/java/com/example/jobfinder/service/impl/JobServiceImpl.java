package com.example.jobfinder.service.impl;

import java.time.YearMonth;
import java.util.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.jobfinder.data.dto.request.PaginationDTO;
import com.example.jobfinder.data.dto.request.job.JobCreationDTO;
import com.example.jobfinder.data.dto.request.job.JobDTO;
import com.example.jobfinder.data.dto.request.job.JobFilterDTO;
import com.example.jobfinder.data.dto.request.job.JobShowDTO;
import com.example.jobfinder.data.dto.request.major.MajorDTO;
import com.example.jobfinder.data.dto.request.position.PositionDTO;
import com.example.jobfinder.data.dto.request.schedule.ScheduleDTO;
import com.example.jobfinder.data.dto.response.ResponseMessage;
import com.example.jobfinder.data.entity.*;
import com.example.jobfinder.data.mapper.*;
import com.example.jobfinder.data.repository.*;
import com.example.jobfinder.exception.ResourceNotFoundException;
import com.example.jobfinder.service.*;
import com.example.jobfinder.utils.enumeration.Estatus;

@Service
public class JobServiceImpl implements JobService {
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobMapper jobMapper;

    @Autowired
    private MajorMapper majorMapper;

    @Autowired
    private ScheduleMapper scheduleMapper;

    @Autowired
    private PositionMapper positionMapper;

    @Autowired
    private StatusService statusService;

    @Autowired
    private JobPositionService jobPositionService;

    @Autowired
    private JobTypeService jobTypeService;

    @Autowired
    private MajorService majorService;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private JobPositionRepository jobPositionRepository;

    @Autowired
    private MajorRepository majorRepository;

    @Autowired
    private JobMajorRepository jobMajorRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private JobScheduleRepository jobScheduleRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private HRRepository hrRepository;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private JobMajorMapper jobMajorMapper;

    @Autowired
    private JobPositionMapper jobPositonMapper;

    @Autowired
    private JobScheduleMapper jobScheduleMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobCareService jobCareService;

    @Autowired
    private CandidateApplicationService candidateApplicationService;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private CandidateApplicationRepository candidateApplicationRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger("info");
    public static final int JOB_STATUS_ACTIVE_ID = 1;

    @Override
    public PaginationDTO filterJob(JobFilterDTO jobFilterDTO, int no, int limit) {
        Specification<Job> spec = Specification.where(JobSpecification.hasName(jobFilterDTO.getName()))
                .and(JobSpecification.hasProvinceName(jobFilterDTO.getProvinceName()))
                .and(JobSpecification.hasPositionIds((jobFilterDTO.getPositionIds())))
                .and(JobSpecification.hasScheduleIds(jobFilterDTO.getScheduleIds()))
                .and(JobSpecification.hasMajorIds(jobFilterDTO.getMajorIds()))
                .and(JobSpecification.hasStatusId(JOB_STATUS_ACTIVE_ID));

        Pageable pageable = PageRequest.of(no, limit, Sort.by("createdDate").descending());
        Page<JobShowDTO> page = this.jobRepository.findAll(spec, pageable).map(item -> jobMapper.toDTOShow(item));

        return new PaginationDTO(
                page.getContent(),
                page.isFirst(),
                page.isLast(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getSize(),
                page.getNumber());
    }

    @Override
    public JobDTO create(JobCreationDTO jobCreationDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        HR hr = this.hrRepository.findByUsername(username).orElseThrow(() -> new AccessDeniedException("FORBIDDEN"));

        Job newJob = this.jobMapper.toEntity(jobCreationDTO);
        newJob.setStatus(statusService.findByName(Estatus.Active.toString()));

        try {
            newJob = this.jobRepository.save(newJob);
            newJob.setCompany(hr.getCompany());

            createJobMajors(newJob, jobCreationDTO.getMajorDTOs());
            createJobPositions(newJob, jobCreationDTO.getPositionDTOs());
            createJobSchedules(newJob, jobCreationDTO.getScheduleDTOs());

            LOGGER.info("Post job successfully");
            return this.jobMapper.toDTO(jobRepository.save(newJob));
        } catch (Exception e) {
            LOGGER.error("Error creating job: " + e.getMessage());
            throw e; // hoặc xử lý ngoại lệ cụ thể nếu cần
        }
    }

    private void createJobMajors(Job job, List<MajorDTO> majorDTOs) {

        if (majorDTOs != null && majorDTOs.size() > 0) {
            for (MajorDTO majorDTO : majorDTOs) {
                Major existingMajor = majorRepository.findById(majorDTO.getId()).orElseThrow();
                JobMajor newJobMajor = new JobMajor();
                newJobMajor.setMajor(existingMajor);
                newJobMajor.setJob(job);
                job.getJobMajors().add(newJobMajor);
                jobMajorRepository.save(newJobMajor);
            }
        }
    }

    private void createJobPositions(Job job, List<PositionDTO> positionDTOs) {
        if (positionDTOs != null && positionDTOs.size() > 0) {
            for (PositionDTO positionDTO : positionDTOs) {
                Position existingPosition = positionRepository.findById((positionDTO.getId()));
                JobPosition newJobPosition = new JobPosition();
                newJobPosition.setPosition(existingPosition);
                newJobPosition.setJob(job);
                job.getJobPositions().add(newJobPosition);
                jobPositionRepository.save(newJobPosition);
            }
        }
    }

    private void createJobSchedules(Job job, List<ScheduleDTO> scheduleDTOs) {
        if (scheduleDTOs != null && scheduleDTOs.size() > 0) {
            for (ScheduleDTO scheduleDTO : scheduleDTOs) {
                Schedule existingSchedule = scheduleRepository.findById(scheduleDTO.getId());
                JobSchedule newJobSchedule = new JobSchedule();
                newJobSchedule.setSchedule(existingSchedule);
                newJobSchedule.setJob(job);
                job.getJobSchedules().add(newJobSchedule);
                jobScheduleRepository.save(newJobSchedule);
            }
        }
    }

    @Override
    public JobDTO update(long id, JobDTO jobUpdateDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        HR hr = this.hrRepository.findByUsername(username).orElseThrow(() -> new AccessDeniedException("FORBIDDEN"));

        Job oldJob = jobRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Collections.singletonMap("id", id)));

        if (oldJob.getCompany().getId() == hr.getCompany().getId()) {
            if (oldJob.getStatus().getStatusId() == JOB_STATUS_ACTIVE_ID) {
                Job newJob = jobMapper.jobUpdateDTOToJob(jobUpdateDTO);
                newJob.setId(oldJob.getId());
                newJob.setStatus(oldJob.getStatus());
                newJob.setCompany(oldJob.getCompany());
                newJob.setJobMajors(updateJobMajors(newJob, jobUpdateDTO.getMajorDTOs()));
                newJob.setJobPositions(updateJobPositions(newJob, jobUpdateDTO.getPositionDTOs()));
                newJob.setJobSchedules(updateJobSchedules(newJob, jobUpdateDTO.getScheduleDTOs()));
                newJob = this.jobRepository.save(newJob);
                return this.jobMapper.toDTO(newJob);
            } else {
                throw new IllegalArgumentException("BAD_REQUEST");
            }
        } else {
            throw new AccessDeniedException("FORBIDDEN");
        }
    }

    private List<JobMajor> updateJobMajors(Job job, List<MajorDTO> majorDTOs) {

        Queue<JobMajor> newJobMajors = new LinkedList<>();
        for (MajorDTO majorDTO : majorDTOs) {
            Major existingMajor = majorRepository.findById(majorDTO.getId()).orElseThrow();

            JobMajor newJobMajor = new JobMajor();
            newJobMajor.setJob(job);
            newJobMajor.setMajor(existingMajor);
            newJobMajors.add(newJobMajor);
            job.getJobMajors().add(newJobMajor);
        }

        List<JobMajor> oldJobMajors = jobMajorRepository.findAllByJob_Id(job.getId());
        Iterator<JobMajor> iterator = oldJobMajors.iterator();
        while (iterator.hasNext()) {
            JobMajor oldJobMajor = iterator.next();
            JobMajor newJobMajor = newJobMajors.poll();

            if (newJobMajor == null) {
                jobMajorRepository.deleteById(oldJobMajor.getId());
            } else {
                newJobMajor.setId(oldJobMajor.getId());
                jobMajorRepository.save(newJobMajor);
                iterator.remove();
            }
        }

        while (!newJobMajors.isEmpty()) {
            jobMajorRepository.save(newJobMajors.poll());
        }
        List<JobMajor> jobMajorList = new ArrayList<>();
        jobMajorList.addAll(newJobMajors);

        return jobMajorList;
    }

    private List<JobPosition> updateJobPositions(Job job, List<PositionDTO> positionDTOs) {

        Queue<JobPosition> newJobPositions = new LinkedList<>();
        for (PositionDTO positionDTO : positionDTOs) {
            Position existingPosition = positionRepository.findById(positionDTO.getId());

            JobPosition newJobPosition = new JobPosition();
            newJobPosition.setJob(job);
            newJobPosition.setPosition(existingPosition);
            newJobPositions.add(newJobPosition);
            job.getJobPositions().add(newJobPosition);
        }
        List<JobPosition> oldJobPositions = jobPositionRepository.findAllByJob_Id(job.getId());
        Iterator<JobPosition> iterator = oldJobPositions.iterator();
        while (iterator.hasNext()) {
            JobPosition oldJobPosition = iterator.next();
            JobPosition newJobPosition = newJobPositions.poll();

            if (newJobPosition == null) {
                jobPositionRepository.deleteById(oldJobPosition.getId());
            } else {
                newJobPosition.setId(oldJobPosition.getId());
                jobPositionRepository.save(newJobPosition);
                iterator.remove();
            }
        }

        while (!newJobPositions.isEmpty()) {
            jobPositionRepository.save(newJobPositions.poll());
        }
        List<JobPosition> jobPositionList = new ArrayList<>();
        jobPositionList.addAll(jobPositionList);

        return jobPositionList;
    }

    private List<JobSchedule> updateJobSchedules(Job job, List<ScheduleDTO> scheduleDTOs) {

        Queue<JobSchedule> newJobSchedules = new LinkedList<>();
        for (ScheduleDTO scheduleDTO : scheduleDTOs) {
            Schedule existingSchedule = scheduleRepository.findById(scheduleDTO.getId());

            JobSchedule newJobSchedule = new JobSchedule();
            newJobSchedule.setJob(job);
            newJobSchedule.setSchedule(existingSchedule);
            newJobSchedules.add(newJobSchedule);
            job.getJobSchedules().add(newJobSchedule);
        }
        List<JobSchedule> oldJobSchedules = jobScheduleRepository.findAllByJob_Id(job.getId());
        Iterator<JobSchedule> iterator = oldJobSchedules.iterator();
        while (iterator.hasNext()) {
            JobSchedule oldJobSchedule = iterator.next();
            JobSchedule newJobSchedule = newJobSchedules.poll();

            if (newJobSchedule == null) {
                jobScheduleRepository.deleteById(oldJobSchedule.getId());
            } else {
                newJobSchedule.setId(oldJobSchedule.getId());
                jobScheduleRepository.save(newJobSchedule);
                iterator.remove();
            }
        }

        while (!newJobSchedules.isEmpty()) {
            jobScheduleRepository.save(newJobSchedules.poll());
        }

        List<JobSchedule> jobScheduleList = new ArrayList<>();
        jobScheduleList.addAll(jobScheduleList);

        return jobScheduleList;
    }

    @Override
    public JobDTO replicate(long id, JobDTO jobDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        HR hr = this.hrRepository.findByUsername(username).orElseThrow(() -> new AccessDeniedException("FORBIDDEN"));

        Job oldJob = jobRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Collections.singletonMap("id", id)));

        if (oldJob.getCompany().getId() == hr.getCompany().getId()) {
            if (oldJob.getStatus().getStatusId() == JOB_STATUS_ACTIVE_ID) {
                Job newJob = jobMapper.jobUpdateDTOToJob(jobDTO);
                newJob.setStatus(statusService.findByName(Estatus.Active.toString()));
                newJob.setCompany(oldJob.getCompany());
                newJob = jobRepository.save(newJob);
                createJobMajors(newJob, jobDTO.getMajorDTOs());
                createJobPositions(newJob, jobDTO.getPositionDTOs());
                createJobSchedules(newJob, jobDTO.getScheduleDTOs());

                return this.jobMapper.toDTO(newJob);
            } else {
                throw new IllegalArgumentException("BAD_REQUEST");
            }
        } else {
            throw new AccessDeniedException("FORBIDDEN");
        }
    }

    @Override
    public JobDTO findById(long id) {

        return this.jobMapper.toDTO(checkOutOfDate(jobRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Collections.singletonMap("id", id)))));
    }

    private Job checkOutOfDate(Job job) {

        if (job.getStatus().getName().equals(String.valueOf(Estatus.Active))
                && job.getEndDate().before(new Date())) {
            job.setStatus(statusService.findByName(String.valueOf(Estatus.Disable)));
        }

        return job;
    }

    //    @Override
    //    public Long recruitmentNews(int month) {
    //        return this.jobRepository.recruitmentNews(month);
    //    }

    @Override
    public PaginationDTO findAllActive(int no, int limit) {

        Page<JobDTO> page =
                jobRepository.findAllActive(PageRequest.of(no, limit)).map(item -> jobMapper.toDTO(item));

        return new PaginationDTO(
                page.getContent(),
                page.isFirst(),
                page.isLast(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getSize(),
                page.getNumber());
    }

    @Override
    public PaginationDTO findAllActiveByCompanyIdShowForHr(int no, int limit) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        HR hr = this.hrRepository.findByUsername(username).orElseThrow(() -> new AccessDeniedException("FORBIDDEN"));

        Page<JobShowDTO> page = jobRepository
                .findAllActiveByCompanyId(hr.getCompany().getId(), PageRequest.of(no, limit))
                .map(job -> {
                    User creator = userRepository
                            .findById(job.getCreatedBy())
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(Collections.singletonMap("id", job.getCreatedBy())));
                    JobShowDTO jobDTO = jobMapper.toDTOShow(job);
                    jobDTO.setCreateBy(creator.getFirstName() + " " + creator.getLastName());
                    return jobDTO;
                });

        return new PaginationDTO(
                page.getContent(),
                page.isFirst(),
                page.isLast(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getSize(),
                page.getNumber());
    }

    @Override
    public PaginationDTO findAllDisableByCompanyIdShowForHr(int no, int limit) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        HR hr = this.hrRepository.findByUsername(username).orElseThrow(() -> new AccessDeniedException("FORBIDDEN"));

        Page<Object> page = jobRepository
                .findAllDisableByCompanyId(hr.getCompany().getId(), PageRequest.of(no, limit))
                .map(job -> {
                    User creator = userRepository
                            .findById(job.getCreatedBy())
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(Collections.singletonMap("id", job.getCreatedBy())));
                    JobShowDTO jobDTO = jobMapper.toDTOShow(job);
                    jobDTO.setCreateBy(creator.getFirstName() + " " + creator.getLastName());
                    return jobDTO;
                });

        return new PaginationDTO(
                page.getContent(),
                page.isFirst(),
                page.isLast(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getSize(),
                page.getNumber());
    }

    @Override
    public PaginationDTO findAllByCompanyIdShowForHr(int no, int limit) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        HR hr = this.hrRepository.findByUsername(username).orElseThrow(() -> new AccessDeniedException("FORBIDDEN"));

        Page<Object> page = jobRepository
                .findAllByCompanyId(hr.getCompany().getId(), PageRequest.of(no, limit))
                .map(job -> {
                    User creator = userRepository
                            .findById(job.getCreatedBy())
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(Collections.singletonMap("id", job.getCreatedBy())));
                    JobShowDTO jobDTO = jobMapper.toDTOShow(job);
                    jobDTO.setCreateBy(creator.getFirstName() + " " + creator.getLastName());
                    return jobDTO;
                });

        return new PaginationDTO(
                page.getContent(),
                page.isFirst(),
                page.isLast(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getSize(),
                page.getNumber());
    }

    @Override
    public long countAllActiveByCompanyIdShowForHr() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        HR hr = this.hrRepository.findByUsername(username).orElseThrow(() -> new AccessDeniedException("FORBIDDEN"));

        return jobRepository.countAllActiveByCompanyId(hr.getCompany().getId());
    }

    @Override
    public long countAllDisableByCompanyIdShowForHr() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        HR hr = this.hrRepository.findByUsername(username).orElseThrow(() -> new AccessDeniedException("FORBIDDEN"));

        return jobRepository.countAllDisableByCompanyId(hr.getCompany().getId());
    }

    @Override
    public long countAllByCompanyId() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        HR hr = this.hrRepository.findByUsername(username).orElseThrow(() -> new AccessDeniedException("FORBIDDEN"));

        return jobRepository.countAllByCompanyId(hr.getCompany().getId());
    }

    //    @Override
    //    public Long countByCreatedDate(LocalDateTime from, LocalDateTime to) {
    //
    //        if (from != null && to != null) {
    //            return jobRepository.countByCreatedDateBetween(from, to);
    //        } else {
    //            return jobPositionService.count();
    //        }
    //    }

    @Override
    public PaginationDTO findAllActiveByCompanyId(long companyId, int no, int limit) {
        // check companyId exits
        Company company = companyService.getById(companyId);
        Page<Object> page = jobRepository
                .findAllByCompanyId(companyId, PageRequest.of(no, limit))
                .map(j -> jobMapper.toDTOShow(j));

        return new PaginationDTO(
                page.getContent(),
                page.isFirst(),
                page.isLast(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getSize(),
                page.getNumber());
    }

    //    @Override
    //    public List<Object[]> getNewStatistics() { // created date within 1 month
    //        return jobRepository
    //                .getNewStatistics(DateTimeHelper.getEarliestTimeOfDate(DateTimeHelper.getDateTimeOfMonthAgo(1)));
    //    }

    @Override
    public Map<YearMonth, int[]> countByMonth() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        HR hr = this.hrRepository.findByUsername(username).orElseThrow(() -> new AccessDeniedException("FORBIDDEN"));

        Map<YearMonth, int[]> counts = new HashMap<>();

        final int NUMBER_OF_MONTHS = 12;
        YearMonth endMonth = YearMonth.now();
        YearMonth startMonth = endMonth.minusMonths(NUMBER_OF_MONTHS);

        for (YearMonth month = startMonth; !month.isAfter(endMonth); month = month.plusMonths(1)) {
            counts.put(month, new int[2]);
        }

        for (Object[] result : jobRepository.countJobsByMonth(hr.getCompany().getId())) {
            YearMonth month = YearMonth.of((int) result[0], (int) result[1]);
            int jobCount = ((Number) result[2]).intValue();
            if (counts.containsKey(month)) {
                counts.get(month)[0] = jobCount;
            }
        }

        for (Object[] result : candidateApplicationRepository.countApplicationsByMonth(
                hr.getCompany().getId())) {
            YearMonth month = YearMonth.of((int) result[0], (int) result[1]);
            int applicationCount = ((Number) result[2]).intValue();
            if (counts.containsKey(month)) {
                counts.get(month)[1] = applicationCount;
            }
        }

        return counts;
    }

    @Override
    public Map<Integer, int[]> countByYear() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        HR hr = this.hrRepository.findByUsername(username).orElseThrow(() -> new AccessDeniedException("FORBIDDEN"));
        Map<Integer, int[]> counts = new HashMap<>();

        final int NUMBER_OF_YEARS = 3;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -NUMBER_OF_YEARS);

        for (int i = 0; i <= NUMBER_OF_YEARS; i++) {
            int year = calendar.get(Calendar.YEAR);
            counts.put(year, new int[2]);
            calendar.add(Calendar.YEAR, 1);
        }

        for (Object[] result : jobRepository.countJobsByYear(hr.getCompany().getId())) {
            int year = (int) result[0];
            int jobCount = ((Number) result[1]).intValue();

            if (counts.containsKey(year)) {
                counts.get(year)[0] = jobCount;
            }
        }

        for (Object[] result : candidateApplicationRepository.countApplicationsByYear(
                hr.getCompany().getId())) {
            int year = (int) result[0];
            int applicationCount = ((Number) result[1]).intValue();

            if (counts.containsKey(year)) {
                counts.get(year)[1] = applicationCount;
            }
        }

        return counts;
    }

    @Transactional
    @Override
    public ResponseMessage delete(long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        HR hr = this.hrRepository.findByUsername(username).orElseThrow(() -> new AccessDeniedException("FORBIDDEN"));

        Job job = this.jobRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Collections.singletonMap("id", id)));

        if (job.getStatus().getName().equals(Estatus.Delete.toString())) {
            throw new ResourceNotFoundException(Collections.singletonMap("id", id));
        }

        if (job.getCompany().getId() != hr.getCompany().getId()) {
            throw new AccessDeniedException("FORBIDDEN");
        }

        Status deleteStatus = statusService.findByName(Estatus.Delete.toString());

        job.setStatus(deleteStatus);

        jobRepository.save(job);

        //            entityManager.clear();
        return ResponseMessage.builder()
                .httpCode(HttpStatus.OK.value())
                .message("Delete success")
                .data(jobMapper.toDTOShow(job))
                .build();
    }

    private void deleteCandidateApplications(List<CandidateApplication> candidateApplications) {
        for (CandidateApplication candidateApplication : candidateApplications) {
            Job job = candidateApplication.getJob();
            job.setStatus(statusService.findByName(Estatus.Delete.toString()));
        }
    }

    @Override
    public ResponseMessage disable(long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        HR hr = this.hrRepository.findByUsername(username).orElseThrow(() -> new AccessDeniedException("FORBIDDEN"));

        Job job = this.jobRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Collections.singletonMap("id", id)));

        if (job.getCompany().getId() != hr.getCompany().getId()) {
            throw new AccessDeniedException("FORBIDDEN");
        }

        if (job.getStatus().getName().equals(Estatus.Delete.toString())) {
            throw new ResourceNotFoundException(Collections.singletonMap("id", id));
        }

        Status disableStatus = statusService.findByName(Estatus.Disable.toString());

        job.setStatus(disableStatus);

        this.jobRepository.save(job);
        return ResponseMessage.builder()
                .httpCode(HttpStatus.OK.value())
                .message("Disable success")
                .data(null)
                .build();
    }

    @Override
    public boolean isAppliable(JobDTO jobDTO) {
        return jobDTO.getStatusDTO().getName().equals(String.valueOf(Estatus.Active));
    }

    private void addJobMajors(Job job, List<Major> majors) {

        if (majors != null && majors.size() > 0) {
            for (Major major : majors) {
                Major existingMajor = majorRepository.findByName(major.getName());
                JobMajor newJobMajor = new JobMajor();
                newJobMajor.setMajor(existingMajor);
                newJobMajor.setJob(job);
                job.getJobMajors().add(newJobMajor);
                jobMajorRepository.save(newJobMajor);
            }
        }
    }

    private void addJobPositions(Job job, List<Position> positions) {
        if (positions != null && positions.size() > 0) {
            for (Position position : positions) {
                Position existingPosition = positionRepository.findByName((position.getName()));
                JobPosition newJobPosition = new JobPosition();
                newJobPosition.setPosition(existingPosition);
                newJobPosition.setJob(job);
                job.getJobPositions().add(newJobPosition);
                jobPositionRepository.save(newJobPosition);
            }
        }
    }

    private void addJobSchedules(Job job, List<Schedule> schedules) {
        if (schedules != null && schedules.size() > 0) {
            for (Schedule schedule : schedules) {
                Schedule existingSchedule = scheduleRepository.findByName(schedule.getName());
                JobSchedule newJobSchedule = new JobSchedule();
                newJobSchedule.setSchedule(existingSchedule);
                newJobSchedule.setJob(job);
                job.getJobSchedules().add(newJobSchedule);
                jobScheduleRepository.save(newJobSchedule);
            }
        }
    }
}
