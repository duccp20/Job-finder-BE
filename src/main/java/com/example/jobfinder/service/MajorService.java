package com.example.jobfinder.service;

import java.util.List;

import com.example.jobfinder.data.dto.request.major.MajorDTO;
import com.example.jobfinder.data.dto.response.ResponseMessage;

public interface MajorService {

    MajorDTO findById(Integer id);

    List<MajorDTO> findAll();

    ResponseMessage create(MajorDTO majorDTO);

    ResponseMessage deleteById(Integer id);
}
