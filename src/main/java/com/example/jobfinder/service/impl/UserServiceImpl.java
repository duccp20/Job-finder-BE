package com.example.jobfinder.service.impl;
import com.example.jobfinder.data.dto.request.user.LoginDTO;
import com.example.jobfinder.data.dto.response.ErrorMessageDTO;
import com.example.jobfinder.data.dto.response.ResponseMessage;
import com.example.jobfinder.data.dto.request.user.UserCreationDTO;
import com.example.jobfinder.data.dto.response.user.LoginResponseDTO;
import com.example.jobfinder.data.dto.response.user.ShowUserDTO;
import com.example.jobfinder.data.entity.Role;
import com.example.jobfinder.data.entity.Status;
import com.example.jobfinder.data.entity.User;
import com.example.jobfinder.data.mapper.UserMapper;
import com.example.jobfinder.data.repository.RoleRepository;
import com.example.jobfinder.data.repository.StatusRepository;
import com.example.jobfinder.data.repository.UserRepository;
import com.example.jobfinder.exception.ConflictException;
import com.example.jobfinder.exception.ResourceNotFoundException;
import com.example.jobfinder.exception.ValidationException;
import com.example.jobfinder.security.jwt.JwtTokenUtils;
import com.example.jobfinder.service.UserService;
import com.example.jobfinder.utils.enumeration.Estatus;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Builder
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HttpServletRequest servletRequest;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private MessageSource messageSource;

    @Override
    public Object register(UserCreationDTO userCreationDTO) {

        boolean existUser = userRepository.existsByEmail(userCreationDTO.getEmail());

        if (existUser) {
            throw new ConflictException(Collections.singletonMap("email", userCreationDTO.getEmail()));
        }

        String encodePassword = passwordEncoder.encode(userCreationDTO.getPassword());
        User user = userMapper.toEntity(userCreationDTO);
        user.setPassword(encodePassword);

        Role role = roleRepository.findByRoleId(userCreationDTO.getRole());
        user.setRole(role);

        String notActiveStatus = Estatus.Not_Active.toString();
        Status status = statusRepository.findByName(notActiveStatus);
        user.setStatus(status);

//        user.setSocialAccount(null);

        userRepository.save(user);
        return new ResponseMessage(201, "Register Successfully", servletRequest.getServletPath());
    }

    @Override
    public void updateTokenActive(String email, String token) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> {
            throw new ResourceNotFoundException(Collections.singletonMap("email", email));
        });
        user.setTokenActive(token);
        userRepository.save(user);
    }

    @Override
    public void updateTokenForgetPassword(String email, String token) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> {
            throw new ResourceNotFoundException(Collections.singletonMap("email", email));
        });
        user.setPasswordForgotToken(token);
        userRepository.save(user);
    }

    @Override
    public Object login(LoginDTO loginDTO) {

        User existingUser = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException(Collections.singletonMap("email or password not valid", loginDTO.getEmail()));
                });

        boolean checkPassword =  passwordEncoder.matches(loginDTO.getPassword(), existingUser.getPassword());
        if(!checkPassword) {
            throw new ValidationException(Collections.singletonMap("email or password not valid", loginDTO.getEmail()));
        }

        boolean isStatusActive = existingUser.getStatus().getName().equals(Estatus.Active.toString());
        if(!isStatusActive) {
            return new ResponseEntity<>(
                    ErrorMessageDTO.builder()
                            .message("Account Not Active")
                            .errors(Collections.singletonMap("email", existingUser.getEmail()))
                            .build(),
                    HttpStatus.BAD_REQUEST
            );

        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(existingUser.getEmail(), loginDTO.getPassword(), existingUser.getAuthorities());

        authenticationManager.authenticate(usernamePasswordAuthenticationToken);


        String accessToken = jwtTokenUtils.generateToken(existingUser);
        String refreshToken = jwtTokenUtils.generateRefreshToken(existingUser);

        ShowUserDTO showUserDTO = userMapper.toShowDTO(existingUser);

        return LoginResponseDTO.builder()
                .message(messageSource.getMessage("message.successLogin", null, null))
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(showUserDTO)
                .build();
    }
}

