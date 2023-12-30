package com.example.jobfinder.controller.candidate;

import com.example.jobfinder.constant.ApiURL;
import com.example.jobfinder.data.dto.request.candidate.CandidateProfileDTO;
import com.example.jobfinder.data.dto.request.mail.EmailRequest;
import com.example.jobfinder.data.dto.response.ResponseMessage;
import com.example.jobfinder.exception.AccessDeniedException;
import com.example.jobfinder.service.CandidateService;
import com.example.jobfinder.service.JsonReaderService;
import com.example.jobfinder.service.MailService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(ApiURL.CANDIDATE)
public class CandidateController {


    @Autowired
    private CandidateService candidateService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private MailService mailService;

    @Autowired
    private JsonReaderService<Object> jsonReaderService;


    @PostMapping("/active-account")
    public ResponseEntity<?> sendMailActive(@Valid @RequestBody EmailRequest emailRequest) throws MessagingException, UnsupportedEncodingException {
        try {
            return ResponseEntity.ok(mailService.sendMailActive(emailRequest.getEmail()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    ;


    @GetMapping("/active")
    public Object activeAccountCandidate(@RequestParam(name = "active-token") String token) {
        try {
            return candidateService.activeCandidate(token);
        } catch (Exception e) {
            String redirectUrl = "http://localhost:3000/verify-email?status=fail";
            return new RedirectView(redirectUrl);
        }
    }


    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize(value = "hasAuthority('Role_Candidate')")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(
            @PathVariable long id,
            @RequestPart(name = "candidateProfileDTO") String candidateProfileDTOJson,
            @RequestPart(name = "fileCV", required = false) MultipartFile fileCV
    ) {
        try {
            if (!candidateService.isCurrentAuthor(id)) {
                throw new AccessDeniedException();
            }
            CandidateProfileDTO candidateProfileDTO = jsonReaderService.readValue(
                    candidateProfileDTOJson, CandidateProfileDTO.class);
            return ResponseEntity.ok(candidateService.updateProfile(id, candidateProfileDTO, fileCV));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize(value = "hasAuthority('Role_Candidate')")
    @PutMapping(value = "/update/image/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateImage(
            @PathVariable long id,
            @RequestPart(name = "fileAvatar", required = false) MultipartFile fileAvatar
    ) throws IOException {
        try {
            if (!candidateService.isCurrentAuthor(id)) {
                throw new AccessDeniedException();
            }
            return ResponseEntity.ok(candidateService.updateAvatar(id, fileAvatar));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize(value = "hasAuthority('Role_Candidate')")
    @PutMapping("/searchable/{id}")
    public ResponseEntity<?> updateSearchable(@PathVariable long id) {

        try {
            if (!candidateService.isCurrentAuthor(id)) {
                throw new AccessDeniedException();
            }
            return ResponseEntity.ok(candidateService.updateSearchable(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize(value = "hasAuthority('Role_Candidate')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getCandidateByUserId(@PathVariable long id) {
        return ResponseEntity.ok(candidateService.getCandidateByUserId(id));
    }
}
