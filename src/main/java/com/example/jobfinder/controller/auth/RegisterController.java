package com.example.jobfinder.controller.auth;


import com.example.jobfinder.constant.ApiURL;
import com.example.jobfinder.data.dto.request.user.UserCreationDTO;
import com.example.jobfinder.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(ApiURL.AUTH)
public class RegisterController {

    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public ResponseEntity<?> doRegister(@Valid  @RequestBody UserCreationDTO userCreationDTO) {
        return new ResponseEntity<>(
                userService.register(userCreationDTO), HttpStatus.CREATED
        );
    }
}
