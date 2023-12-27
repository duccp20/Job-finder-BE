package com.example.jobfinder.service;

import com.example.jobfinder.data.dto.request.PaginationDTO;
import com.example.jobfinder.data.dto.request.company.CompanyDTO;
import com.example.jobfinder.data.entity.Company;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

public interface CompanyService {

	PaginationDTO findAllByNameLike(String name, int no, int limit);
	//
	PaginationDTO findAll(int no, int limit);

	PaginationDTO findAllActive(int no, int limit);

	CompanyDTO findByJobId(long jobId);

	@Transactional
	CompanyDTO create(CompanyDTO companyDTO, MultipartFile fileLogo) throws IOException;

	@Transactional
	CompanyDTO update(long id, CompanyDTO companyDTO, MultipartFile fileLogo) throws IOException;

	// Find by ID ---> Get by ID
	CompanyDTO findById(long id);

	Company getById(long id);

	boolean deleteById(long id);

	Long countByCreatedDate(Date from, Date to);

	// Map<String, String> checkCompany(int id, CompanyDTO companyDTO);
	//
	// Map<String, String> checkCompany(CompanyDTO companyDTO);

	// CompanyDTO readJson(String value, MultipartFile fileLogo);

	// void flush();

	// List<Object[]> getStatusStatistics();

	// List<Object[]> getNewStatistics();

}
