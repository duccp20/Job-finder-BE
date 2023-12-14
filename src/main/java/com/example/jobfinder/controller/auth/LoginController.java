package com.example.jobfinder.controller.auth;
import com.example.jobfinder.constant.ApiURL;
import com.example.jobfinder.data.dto.request.user.LoginDTO;
import com.example.jobfinder.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiURL.USER)
@CrossOrigin(origins = "*", maxAge = 3600)
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
