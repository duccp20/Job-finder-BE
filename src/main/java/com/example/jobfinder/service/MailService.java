package com.example.jobfinder.service;

import java.io.UnsupportedEncodingException;

import jakarta.mail.MessagingException;

import com.example.jobfinder.data.dto.response.ResponseMessage;
import com.example.jobfinder.data.dto.response.mail.MailResponse;

public interface MailService {

    ResponseMessage sendMailActive(String email) throws MessagingException, UnsupportedEncodingException;

    void send(MailResponse mail) throws MessagingException, UnsupportedEncodingException;

    ResponseMessage sendTokenForgetPassword(String email) throws MessagingException, UnsupportedEncodingException;
}
