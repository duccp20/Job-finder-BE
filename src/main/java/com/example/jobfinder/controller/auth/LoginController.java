package com.example.jobfinder.controller.auth;
import com.example.jobfinder.constant.ApiURL;
import com.example.jobfinder.data.dto.request.user.LoginDTO;
import com.example.jobfinder.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiURL.USER)
@RequiredArgsConstructor
public class LoginController {

    final private UserService userService;
    @PostMapping("/login")
    public ResponseEntity<?> register(@Valid @RequestBody LoginDTO loginDTO) {
        return new ResponseEntity<>(
                userService.login(loginDTO), HttpStatus.OK
        );
    }
}
