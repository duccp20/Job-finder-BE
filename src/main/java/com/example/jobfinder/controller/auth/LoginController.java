package com.example.jobfinder.controller.auth;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.jobfinder.constant.ApiURL;
import com.example.jobfinder.data.dto.request.user.LoginDTO;
import com.example.jobfinder.data.dto.response.user.LoginResponseDTO;
import com.example.jobfinder.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiURL.USER)
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> handleLogin(@Valid @RequestBody LoginDTO loginDTO) {

        return userService.login(loginDTO);
    }
}
