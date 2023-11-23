package com.example.jobfinder.service.impl;


import com.example.jobfinder.data.dto.response.mail.MailResponse;
import com.example.jobfinder.data.entity.Token;
import com.example.jobfinder.data.entity.User;
import com.example.jobfinder.data.repository.UserRepository;
import com.example.jobfinder.exception.ResourceNotFoundException;
import com.example.jobfinder.service.MailService;
import com.example.jobfinder.service.TokenRepository;
import com.example.jobfinder.service.TokenService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.UUID;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Value("${domain.path:}")
    private String url;


    @Override
    public String sendSimpleMail(MailResponse details) {
        return null;
    }

    @Override
    public void sendMailWithAttachment(MailResponse details) throws MessagingException, UnsupportedEncodingException {

        User user = userRepository.findByEmail(details.getNameReceive()).orElseThrow(() -> {
                    throw new ResourceNotFoundException(Collections.singletonMap("email", details.getNameReceive()));
                }
        );


        String urlDirect = url + "test thôi, chưa xác thực đâu";
        Token token = tokenService.generateToken(user);
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        messageHelper.setFrom(sender, details.getNamePost());
        messageHelper.setTo(user.getEmail());
        messageHelper.setText(new MailResponse().createMailConfirm(urlDirect, token.getToken()), true);
        messageHelper.setSubject(details.getSubject());

        // Sending mail
        mailSender.send(message);
    }

    @Override
    public Object sendMailActive(String email) throws MessagingException, UnsupportedEncodingException {

        MailResponse mailResponse = MailResponse.builder()
                .namePost("job-finder")
                .subject("Xác thực email cho tài khoản Jobsit.vn")
                .nameReceive(email)
                .build();


        this.sendMailWithAttachment(mailResponse);

        return new ResponseEntity<>("Send mail Completed!", HttpStatus.OK);
    }


}
