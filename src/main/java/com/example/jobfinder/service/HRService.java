package com.example.jobfinder.service;

import java.io.IOException;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.jobfinder.data.dto.request.PaginationDTO;
import com.example.jobfinder.data.dto.request.hr.HRCreationDTO;
import com.example.jobfinder.data.dto.request.hr.HRDTO;
import com.example.jobfinder.data.dto.request.hr.HRProfileDTO;

public interface HRService {

    PaginationDTO findAll(int no, int limit);

    HRDTO findById(long id);

    HRDTO findByUserId(long userId);

    @Transactional
    Object create(HRCreationDTO hrCreationDTO, MultipartFile fileAvatar) throws IOException;

    @Transactional
    Object updateHRInfo(HRProfileDTO hrProfileDTO, MultipartFile fileAvatar) throws IOException;
}
