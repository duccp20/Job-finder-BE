package com.example.jobfinder.controller;

import com.example.jobfinder.constant.ApiURL;

import com.example.jobfinder.service.MailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(ApiURL.MAIL)
public class MailController {
    @Autowired
    private MailService mailService;

    @GetMapping("/active-user")
    public ResponseEntity<?> sendMailActive(@RequestParam String email) throws MessagingException, UnsupportedEncodingException {
        try {
           return  ResponseEntity.ok(mailService.sendMailActive(email));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    };
}
