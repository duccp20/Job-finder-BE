package com.example.jobfinder.controller.auth;


import com.example.jobfinder.constant.ApiURL;
import com.example.jobfinder.data.dto.request.user.LoginDTO;
import com.example.jobfinder.data.dto.request.user.UserCreationDTO;
import com.example.jobfinder.data.dto.response.ApiResponse;
import com.example.jobfinder.data.dto.response.user.LoginResponseDTO;
import com.example.jobfinder.data.dto.response.user.ShowUserDTO;
import com.example.jobfinder.service.AuthService;
import com.example.jobfinder.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiURL.AUTH)
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<ShowUserDTO>> handleRegister(@Valid @RequestBody UserCreationDTO userCreationDTO) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<ShowUserDTO>builder()
                        .message("Register successfully")
                        .httpCode(HttpStatus.CREATED.value())
                        .data(authService.handleRegister(userCreationDTO))
                        .build());
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> handleLogin(@Valid @RequestBody LoginDTO loginDTO) {

        return userService.login(loginDTO);
    }


}
