package com.example.jobfinder.service;

import com.example.jobfinder.data.dto.response.ResponseMessage;
import com.example.jobfinder.data.dto.response.mail.MailResponse;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface MailService{

    String sendSimpleMail(MailResponse details);

    void sendMailWithAttachment(MailResponse details) throws MessagingException, UnsupportedEncodingException;

    Object sendMailActive(String email) throws MessagingException, UnsupportedEncodingException;
}

