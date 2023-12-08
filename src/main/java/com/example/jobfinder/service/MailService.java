package com.example.jobfinder.service;

import com.example.jobfinder.data.dto.response.ResponseMessage;
import com.example.jobfinder.data.dto.response.mail.MailResponse;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface MailService{

    ResponseMessage sendMailActive(String email) throws MessagingException, UnsupportedEncodingException;

    void send(MailResponse mail) throws MessagingException, UnsupportedEncodingException;


    ResponseMessage sendTokenForgetPassword(String email) throws MessagingException, UnsupportedEncodingException;
}

