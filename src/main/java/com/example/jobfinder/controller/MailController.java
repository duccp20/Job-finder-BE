package com.example.jobfinder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.jobfinder.constant.ApiURL;
import com.example.jobfinder.service.MailService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(ApiURL.MAIL)
public class MailController {
    @Autowired
    private MailService mailService;

    //    @GetMapping("/active-user")
    //    public ResponseEntity<?> sendMailActive(@RequestParam String email) throws MessagingException,
    // UnsupportedEncodingException {
    //        try {
    //           return  ResponseEntity.ok(mailService.sendMailActive(email));
    //        } catch (Exception e) {
    //            return ResponseEntity.badRequest().body(e.getMessage());
    //        }
    //    };
}
