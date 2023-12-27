package com.example.jobfinder.service.impl;


import com.example.jobfinder.data.dto.request.PaginationDTO;
import com.example.jobfinder.data.dto.request.hr.HRCreationDTO;
import com.example.jobfinder.data.dto.request.hr.HRDTO;
import com.example.jobfinder.data.dto.request.hr.HRProfileDTO;
import com.example.jobfinder.data.dto.request.user.UserDTO;
import com.example.jobfinder.data.dto.response.ResponseMessage;
import com.example.jobfinder.data.entity.HR;
import com.example.jobfinder.data.mapper.HRMapper;
import com.example.jobfinder.data.repository.HRRepository;
import com.example.jobfinder.exception.AccessDeniedException;
import com.example.jobfinder.exception.ResourceNotFoundException;
import com.example.jobfinder.service.CompanyService;
import com.example.jobfinder.service.HRService;
import com.example.jobfinder.service.UserService;
import com.example.jobfinder.utils.enumeration.ERole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class HRServiceImpl implements HRService {

    @Autowired
    private HRRepository hrRepository;
    @Autowired
    private HRMapper hrMapper;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private UserService userService;

//    public final static Logger LOGGER = LoggerFactory.getLogger("info");

    @Override
    public PaginationDTO findAll(int no, int limit) {
        Page<HRDTO> page = hrRepository.findAll(PageRequest.of(no, limit)).map(hr -> hrMapper.toDTO(hr));
        return new PaginationDTO(page.getContent(), page.isFirst(), page.isLast(), page.getTotalPages(),
                page.getTotalElements(), page.getSize(), page.getNumber());
    }

    @Override
    public HRDTO findById(long id) {
        return hrMapper.toDTO(
                hrRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(Collections.singletonMap("id", id))));
    }

    @Override
    public HRDTO findByUserId(long userId) {
        return hrMapper.toDTO(
                hrRepository.findByUser_Id(userId)
                        .orElseThrow(() -> new ResourceNotFoundException(Collections.singletonMap("id", userId))));
    }

    @Override
    public Object create(HRCreationDTO hrCreationDTO, MultipartFile fileAvatar) throws IOException {
        // save user first
        UserDTO createdUserDTO = userService.create(hrCreationDTO.getUserCreationDTO(), fileAvatar, ERole.HR);

        // if not exists hr's company when hr register, create new company first
        if (hrCreationDTO.getHrOtherInfoDTO().getCompanyDTO().getId() == null) {
            hrCreationDTO.getHrOtherInfoDTO()
                    .setCompanyDTO(companyService.create(hrCreationDTO.getHrOtherInfoDTO().getCompanyDTO(), null));
        } else {
            hrCreationDTO.getHrOtherInfoDTO()
                    .setCompanyDTO(companyService.findById(hrCreationDTO.getHrOtherInfoDTO().getCompanyDTO().getId()));
        }

        HR hr = hrMapper.toEntity(hrCreationDTO);
        hr.getUser().setId(createdUserDTO.getId());

        HRDTO hrDTO = hrMapper.toDTO(hrRepository.save(hr));
        hrDTO.setUserDTO(createdUserDTO);
        return ResponseMessage.builder()
                .httpCode(201)
                .message("HR created successfully")
                .data(hrDTO)
                .build();
    }


    @Override
    public Object updateHRInfo(HRProfileDTO hrProfileDTO, MultipartFile fileAvatar) throws IOException {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        HR hr = this.hrRepository.findByUsername(username).orElseThrow(
                () -> new AccessDeniedException(Collections.singletonMap("username", username)));

        UserDTO updatedUserDTO = userService.update(
                hr.getUser().getId(), hrProfileDTO.getUserProfileDTO(), fileAvatar);

//        HR updateHR = hrMapper.toEntity(hrProfileDTO);
//        updateHR.setId(hr.getId());
//        updateHR.getUser().setId(updatedUserDTO.getId());
//        updateHR.setCompany(hr.getCompany());
        hr.setPosition(hrProfileDTO.getPosition());

        Map<String, Object> showData = new HashMap<>();
        showData.put("showUserDTO", updatedUserDTO);
        showData.put("hrDTO", hrMapper.toDTO(hrRepository.save(hr)));


        return showData;
    }
}
