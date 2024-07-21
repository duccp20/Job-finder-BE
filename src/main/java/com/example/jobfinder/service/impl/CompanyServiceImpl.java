package com.example.jobfinder.service.impl;

import java.io.IOException;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.jobfinder.data.dto.request.PaginationDTO;
import com.example.jobfinder.data.dto.request.company.CompanyDTO;
import com.example.jobfinder.data.entity.Company;
import com.example.jobfinder.data.entity.HR;
import com.example.jobfinder.data.mapper.CompanyMapper;
import com.example.jobfinder.data.mapper.StatusMapper;
import com.example.jobfinder.data.repository.CompanyRepository;
import com.example.jobfinder.data.repository.HRRepository;
import com.example.jobfinder.data.repository.StatusRepository;
import com.example.jobfinder.exception.InternalServerErrorException;
import com.example.jobfinder.exception.ResourceNotFoundException;
import com.example.jobfinder.service.CompanyService;
import com.example.jobfinder.service.FileService;
import com.example.jobfinder.service.StatusService;
import com.example.jobfinder.utils.common.UpdateFile;
import com.example.jobfinder.utils.enumeration.Estatus;

@Service
public class CompanyServiceImpl implements CompanyService {
    //    @Autowired
    //    private UpdateFile updateFile;
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private StatusService statusService;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private StatusMapper statusMapper;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private FileService fileService;

    @Autowired
    private HRRepository hrRepository;

    @Autowired
    private UpdateFile updateFile;

    public static final Logger LOGGER = LoggerFactory.getLogger("info");

    // Find by ID ---> Get by ID
    @Override
    public CompanyDTO findById(long id) {
        return this.companyMapper.toDTO(this.getById(id));
    }

    // Save
    @Override
    public CompanyDTO create(CompanyDTO companyDTO, MultipartFile fileLogo) throws IOException {
        // check exists company info
        Map<String, String> errors = new HashMap<>();
        if (companyRepository.existsByName(companyDTO.getName())) {
            errors.put("Name", messageSource.getMessage("error.companyNameExists", null, null));
        }
        if (companyRepository.existsByEmail(companyDTO.getEmail())) {
            errors.put("Email", messageSource.getMessage("error.companyEmailExists", null, null));
        }
        if (companyRepository.existsByTax(companyDTO.getName())) {
            errors.put("Tax", messageSource.getMessage("error.companyTaxExists", null, null));
        }
        if (companyRepository.existsByWebsite(companyDTO.getName())) {
            errors.put("Website", messageSource.getMessage("error.companyEmailExists", null, null));
        }
        if (errors.size() > 0) {
            throw new InternalServerErrorException(errors);
        }

        Company company = this.companyMapper.toEntity(companyDTO);
        //        company.setLogo(fileService.uploadFile(fileLogo));
        company.setLogo(updateFile.uploadImage(fileLogo));
        company.setLocation(null); // update
        company.setStatus(this.statusRepository
                .findByName(Estatus.Active.toString())
                .orElseThrow(() ->
                        new ResourceNotFoundException(Collections.singletonMap("name", Estatus.Active.toString()))));

        return this.companyMapper.toDTO(companyRepository.save(company));
    }

    @Override
    public CompanyDTO update(long id, CompanyDTO companyDTO, MultipartFile fileLogo) throws IOException {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        HR hr = this.hrRepository.findByUsername(username).orElseThrow(() -> new AccessDeniedException("FORBIDDEN"));

        Company oldCompany = companyRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Collections.singletonMap("id", id)));

        if (hr.getCompany().getId() == id) {
            if (companyRepository.existsByIdNotAndEmail(oldCompany.getId(), companyDTO.getEmail())) {
                throw new IllegalArgumentException("BAD_REQUEST");
            }

            if (companyRepository.existsByIdNotAndWebsite(oldCompany.getId(), companyDTO.getWebsite())) {
                throw new IllegalArgumentException("BAD_REQUEST");
            }

            if (companyRepository.existsByIdNotAndPhone(oldCompany.getId(), companyDTO.getPhone())) {
                throw new IllegalArgumentException("BAD_REQUEST");
            }

            if (companyRepository.existsByIdNotAndTax(oldCompany.getId(), companyDTO.getTax())) {
                throw new IllegalArgumentException("BAD_REQUEST");
            }

            Company updateCompany = companyMapper.toEntity(companyDTO);
            if (oldCompany.getLogo() != null && !oldCompany.getLogo().isEmpty()) {
                fileService.deleteFile(oldCompany.getLogo());
            }

            //            updateCompany.setLogo(fileService.uploadFile(fileLogo));
            updateCompany.setLogo(updateFile.uploadImage(fileLogo));
            updateCompany.setId(oldCompany.getId());
            updateCompany.setStatus(oldCompany.getStatus());

            return companyMapper.toDTO(companyRepository.save(updateCompany));
        } else {
            throw new AccessDeniedException("FORBIDDEN");
        }
    }

    // Get by ID
    @Override
    public Company getById(long id) {

        return companyRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Collections.singletonMap("id", id)));
    }

    // Find by name
    @Override
    public PaginationDTO findAllByNameLike(String name, int no, int limit) {

        Page<CompanyDTO> page = this.companyRepository
                .findAllByNameLike(name, PageRequest.of(no, limit))
                .map(c -> this.companyMapper.toDTO(c));

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
    public PaginationDTO findAll(int no, int limit) {

        Page<CompanyDTO> page =
                this.companyRepository.findAll(PageRequest.of(no, limit)).map(c -> this.companyMapper.toDTO(c));

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
    public PaginationDTO findAllActive(int no, int limit) {
        Page<CompanyDTO> page = this.companyRepository
                .findAllByStatus_Name(Estatus.Active.toString(), PageRequest.of(no, limit))
                .map(c -> this.companyMapper.toDTO(c));
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
    public boolean deleteById(long id) {

        Company company = companyRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Collections.singletonMap("id", id)));

        company.setStatus(statusRepository
                .findByName(Estatus.Delete.toString())
                .orElseThrow(() ->
                        new ResourceNotFoundException(Collections.singletonMap("name", Estatus.Delete.toString()))));
        companyRepository.save(company);

        return true;
    }

    @Override
    public CompanyDTO findByJobId(long jobId) {

        Optional<Company> company = companyRepository.findByJobId(jobId);

        if (company.isPresent()) {
            return companyMapper.toDTO(company.get());
        }

        return null;
    }

    @Override
    public Long countByCreatedDate(Date from, Date to) {
        return companyRepository.countByCreatedDateBetween(from, to);
    }
}
