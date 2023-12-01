package com.example.jobfinder.controller.candidate;

import com.example.jobfinder.constant.ApiURL;
import com.example.jobfinder.service.CandidateService;
import com.example.jobfinder.service.MailService;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping(ApiURL.CANDIDATE)
public class CandidateController {


    @Autowired
    private CandidateService candidateService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private MailService mailService;


    @PostMapping("/send-mail-active/{email}")
    public ResponseEntity<?> sendMailActive(@Valid @PathVariable String email) throws MessagingException, UnsupportedEncodingException {
        return new ResponseEntity<>(
                mailService.sendMailActive(email),
                HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<?> activeAccountCandidate(@RequestParam(name = "activeToken") String token) {
        RedirectView redirectView = new RedirectView();
        try {
            this.candidateService.activeCandidate(token);
            String redirectUrl = "http://localhost:3000/auth/confirmActive?status=success&message="
                    + messageSource.getMessage("error.activeUserSuccessful", null, null);

            redirectView.setUrl(redirectUrl);
            return ResponseEntity.ok(redirectView);
        } catch (Exception ex) {
            String redirectUrl = "http://localhost:3000/auth/confirmActive?status=failed&message=" + ex.getMessage();

            redirectView.setUrl(redirectUrl);
            return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }



}
