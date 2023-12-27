package com.example.jobfinder.controller.hr;

import com.example.jobfinder.constant.ApiURL;
import com.example.jobfinder.constant.PageDefault;
import com.example.jobfinder.data.dto.request.candidate.CandidateProfileDTO;
import com.example.jobfinder.data.dto.request.hr.HRCreationDTO;
import com.example.jobfinder.data.dto.request.hr.HRProfileDTO;
import com.example.jobfinder.service.HRService;
import com.example.jobfinder.service.JsonReaderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(ApiURL.HR)
public class HRController {
    @Autowired
    private HRService hrService;

    @Autowired
    private JsonReaderService<Object> jsonReaderService;

    @GetMapping("")
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = PageDefault.NO) int no,
                                     @RequestParam(defaultValue = PageDefault.LIMIT) int limit) {
        return ResponseEntity.ok(hrService.findAll(no, limit));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_HR')")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") long id) {
        return ResponseEntity.ok(hrService.findById(id));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_HR')")
    @GetMapping("/user/{id}")
    public ResponseEntity<?> findByUserId(@PathVariable("id") long userId) {
        return ResponseEntity.ok(hrService.findByUserId(userId));
    }

    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> create(@Valid @RequestPart HRCreationDTO hrCreationDTO,
                                    @RequestPart(name = "fileAvatar", required = false) MultipartFile fileAvatar) throws IOException {
        try {
            return new ResponseEntity<>(hrService.create(hrCreationDTO, fileAvatar), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_HR')")
    @PutMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateHRInfo(@Valid @RequestPart(name = "hrProfileDTO") String hrProfileDTOJson,
            @RequestPart(name = "fileAvatar", required = false) MultipartFile fileAvatar) throws IOException {
        HRProfileDTO hrProfileDTO = jsonReaderService.readValue(
                hrProfileDTOJson, HRProfileDTO.class);
        return ResponseEntity.ok(hrService.updateHRInfo(hrProfileDTO, fileAvatar));
    }
}
