package com.example.jobfinder.service;

import com.example.jobfinder.data.entity.Token;
import com.example.jobfinder.data.entity.User;

public interface TokenService {

    Token generateToken(User user);
}
