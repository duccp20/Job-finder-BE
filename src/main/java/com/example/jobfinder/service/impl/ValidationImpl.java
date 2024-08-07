package com.example.jobfinder.service.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.example.jobfinder.service.Validation;

@Component
public class ValidationImpl implements Validation {
    private static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]*$";
    private Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    @Override
    public boolean passwordValid(String password) {
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
