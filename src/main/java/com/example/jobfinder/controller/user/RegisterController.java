package com.example.jobfinder.controller.user;


import com.example.jobfinder.constant.ApiURL;
import com.example.jobfinder.data.dto.user.UserCreationDTO;
import com.example.jobfinder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiURL.AUTH)
public class RegisterController {

    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserCreationDTO userCreationDTO) {
        return new ResponseEntity<>(
                userService.register(userCreationDTO), HttpStatus.CREATED
                );
    }
}
