package com.example.jobfinder.service;

import com.example.jobfinder.data.dto.request.major.MajorDTO;
import com.example.jobfinder.data.dto.response.ResponseMessage;

import java.util.List;

public interface MajorService {

	MajorDTO findById(Integer id);

	List<MajorDTO> findAll();

	ResponseMessage create(MajorDTO majorDTO);

	ResponseMessage deleteById(Integer id);

}
