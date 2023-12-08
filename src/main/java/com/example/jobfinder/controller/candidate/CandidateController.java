package com.example.jobfinder.controller.candidate;

import com.example.jobfinder.constant.ApiURL;
import com.example.jobfinder.data.dto.request.mail.EmailRequest;
import com.example.jobfinder.service.CandidateService;
import com.example.jobfinder.service.MailService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.UnsupportedEncodingException;
import java.util.Collections;

@RestController
@RequestMapping(ApiURL.CANDIDATE)
public class CandidateController {


    @Autowired
    private CandidateService candidateService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private MailService mailService;


    @PostMapping("/active-account")
    public ResponseEntity<?> sendMailActive(@Valid @RequestBody EmailRequest emailRequest) throws MessagingException, UnsupportedEncodingException {
        // Xử lý email
        return new ResponseEntity<>(
                mailService.sendMailActive(emailRequest.getEmail()),
                HttpStatus.OK);
    }



    @GetMapping("/active")
    public Object activeAccountCandidate(@RequestParam(name = "active-token") String token) {
        try {
           return candidateService.activeCandidate(token);
        }
        catch (Exception e) {
            String redirectUrl = "http://localhost:3000/verify-email?status=fail";
            return new RedirectView(redirectUrl);
        }
    }

}
