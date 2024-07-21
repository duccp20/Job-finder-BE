package com.example.jobfinder.service;

import java.io.UnsupportedEncodingException;

import jakarta.mail.MessagingException;

import com.example.jobfinder.data.dto.response.ApiResponse;
import com.example.jobfinder.data.dto.response.mail.MailResponse;

public interface MailService {

    ApiResponse sendMailActive(String email) throws MessagingException, UnsupportedEncodingException;

    void send(MailResponse mail) throws MessagingException, UnsupportedEncodingException;

    ApiResponse sendTokenForgetPassword(String email) throws MessagingException, UnsupportedEncodingException;
}
