package com.example.jobfinder.service.impl;

import java.io.IOException;
import java.util.*;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import com.example.jobfinder.config.JwtTokenUtils;
import com.example.jobfinder.data.dto.request.ChangePasswordDTO;
import com.example.jobfinder.data.dto.request.user.*;
import com.example.jobfinder.data.dto.response.ResponseMessage;
import com.example.jobfinder.data.dto.response.user.LoginResponseDTO;
import com.example.jobfinder.data.entity.*;
import com.example.jobfinder.data.mapper.UserMapper;
import com.example.jobfinder.data.repository.CandidateRepository;
import com.example.jobfinder.data.repository.RoleRepository;
import com.example.jobfinder.data.repository.StatusRepository;
import com.example.jobfinder.data.repository.UserRepository;
import com.example.jobfinder.exception.ConflictException;
import com.example.jobfinder.exception.InternalServerErrorException;
import com.example.jobfinder.exception.ResourceNotFoundException;
import com.example.jobfinder.exception.ValidationException;
import com.example.jobfinder.service.*;
import com.example.jobfinder.utils.common.UpdateFile;
import com.example.jobfinder.utils.enumeration.ERole;
import com.example.jobfinder.utils.enumeration.Estatus;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Service
@Builder
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final StatusRepository statusRepository;

    private final UserMapper userMapper;

    private final HttpServletRequest servletRequest;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final JwtTokenUtils jwtTokenUtils;

    private final MessageSource messageSource;

    private final TokenService tokenService;

    private final StatusService statusService;

    private final TokenRepository tokenRepository;

    private final Validation validation;

    private final CandidateRepository candidateRepository;

    //    private final AuditorAware<Long> auditorAware;

    private final FileService fileService;

    private final UpdateFile updateFile;

    @Override
    public Object register(UserCreationDTO userCreationDTO) {

        boolean existUser = userRepository.existsByEmail(userCreationDTO.getEmail());

        if (existUser) {
            throw new ConflictException(Collections.singletonMap("email", userCreationDTO.getEmail()));
        }

        String encodePassword = passwordEncoder.encode(userCreationDTO.getPassword());
        User user = userMapper.toEntity(userCreationDTO);
        user.setPassword(encodePassword);

        Role role = roleRepository
                .findByName(ERole.Candidate.toString())
                .orElseThrow(() ->
                        new ResourceNotFoundException(Collections.singletonMap("role", ERole.Candidate.toString())));
        user.setRole(role);

        String notActiveStatus = Estatus.Not_Active.toString();
        Status status = statusRepository
                .findByName(notActiveStatus)
                .orElseThrow(() -> new ResourceNotFoundException(Collections.singletonMap("status", notActiveStatus)));

        user.setStatus(status);

        userRepository.save(user);

        if (user.getRole().getName().equals(ERole.Candidate.toString())) {
            Candidate candidate = new Candidate();
            candidate.setUser(user);
            candidateRepository.save(candidate);
        }

        return ResponseMessage.builder()
                .httpCode(HttpStatus.CREATED.value())
                .message("Register Successfully")
                .data(user)
                .build();
        //        return new ResponseMessage(201, "Register Successfully", , servletRequest.getServletPath());
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
    public void updateRefreshToken(String email, String refreshToken) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> {
            throw new ResourceNotFoundException(Collections.singletonMap("email", email));
        });
        user.setRefreshToken(refreshToken);
        userRepository.save(user);
    }

    @Override
    public ResponseEntity<LoginResponseDTO> login(LoginDTO loginDTO) {

        User existingUser = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(() -> {
            throw new ResourceNotFoundException(Collections.singletonMap("Email không tồn tại", loginDTO.getEmail()));
        });
        //
        //        boolean checkPassword = passwordEncoder.matches(loginDTO.getPassword(), existingUser.getPassword());
        //        if (!checkPassword) {
        //            throw new ValidationException(Collections.singletonMap("Sai mật khẩu", loginDTO.getEmail()));
        //        }
        //
        //        boolean isStatusActive = existingUser.getStatus().getName().equals(Estatus.Active.toString());
        //        if (!isStatusActive) {
        //            return ResponseMessage.builder()
        //                    .httpCode(HttpStatus.UNAUTHORIZED.value())
        //                    .message("Email chưa được kích hoạt")
        //                    .build();
        //        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());

        Authentication authentication =
                authenticationManagerBuilder.getObject().authenticate(usernamePasswordAuthenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // create access token
        String accessToken = this.jwtTokenUtils.createAccessToken(existingUser);

        // create refresh token
        String refreshToken = this.jwtTokenUtils.createRefreshToken(existingUser);

        this.updateRefreshToken(existingUser.getEmail(), refreshToken);

        List<Token> numsToken = tokenRepository.findAllByUser(existingUser);

        // save access token to database
        //        Token newToken = Token.builder()
        //                .token(accessToken)
        //                .user(existingUser)
        //                .status(statusService.findByName(Estatus.Active.toString()))
        //                .build();
        //
        //        this.tokenRepository.save(newToken);
        //
        //        if (numsToken.size() > 3) {
        //            tokenRepository.delete(numsToken.get(0));
        //        }

        UserDTO showUserDTO = userMapper.toDTO(existingUser);

        // add refresh token to response and cookies

        //  Set refresh token to cookie
        ResponseCookie responseCookie = ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)
                .secure(true) // only work with https, but localhost thi k co tac dung
                .path("/") // url set cookie
                .maxAge(this.jwtTokenUtils.getRefreshTokenExpiration())
                // .domain("example.com")  nếu không set, thì chỉ chính là same domain (không tính sub domain)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(LoginResponseDTO.builder()
                        .httpCode(200)
                        .message(messageSource.getMessage("message.successLogin", null, null))
                        .accessToken(accessToken)
                        .data(showUserDTO)
                        .build());
    }

    @Override
    public ResponseMessage changePassword(ChangePasswordDTO changePasswordDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new InternalServerErrorException(Collections.singletonMap("email", email)));

        if (checkValidOldPassword(user.getPassword(), changePasswordDTO.getOldPassword())) {
            if (!validation.passwordValid(changePasswordDTO.getNewPassword()))
                throw new InternalServerErrorException(messageSource.getMessage("error.passwordRegex", null, null));
            user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
            userRepository.save(user);
        } else {
            throw new ValidationException(Collections.singletonMap("oldPassword", changePasswordDTO.getOldPassword()));
        }

        return ResponseMessage.builder()
                .httpCode(200)
                .message("Change password successfully")
                .data(userMapper.toDTO(user))
                .build();
    }

    @Override
    public boolean checkValidOldPassword(String oldPass, String confirmPass) {
        return passwordEncoder.matches(confirmPass, oldPass);
    }

    @Override
    public Object activeForgetPassword(String token) {

        User user = userRepository
                .findByPasswordForgotToken(token)
                .orElseThrow(() ->
                        new InternalServerErrorException(this.messageSource.getMessage("Token not found", null, null)));

        String activeToken = user.getTokenActive();

        //        boolean tokenExpired = jwtTokenUtils.isTokenExpired(activeToken);

        //        if (tokenExpired) {
        //            return new RedirectView("http://localhost:3000/forgot-password/verify?status=fail");
        //        }

        return new RedirectView("http://localhost:3000/reset-password?token=" + token);
    }

    @Override
    public void resetPasswordByToken(ResetPasswordByToken resetPasswordByTokenDTO) {

        User user = this.userRepository
                .findByPasswordForgotToken(resetPasswordByTokenDTO.getToken())
                .orElseThrow(() -> new InternalServerErrorException(
                        this.messageSource.getMessage("error.tokenNotFound", null, null)));

        String activeToken = user.getTokenActive();

        //        boolean tokenExpired = jwtTokenUtils.isTokenExpired(activeToken);

        //        if (tokenExpired) {
        //            throw new InternalServerErrorException(this.messageSource.getMessage("error.tokenIsExpired", null,
        // null));
        //        }

        if (!this.validation.passwordValid(resetPasswordByTokenDTO.getNewPassword())) // Check Password is strong
        throw new InternalServerErrorException(messageSource.getMessage("error.passwordRegex", null, null));

        String newPasswordEncoder = passwordEncoder.encode(resetPasswordByTokenDTO.getNewPassword());
        user.setPassword(newPasswordEncoder);
        user.setPasswordForgotToken("");

        this.userRepository.save(user);
    }

    @Override
    public Object getUserProfile() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(Collections.singletonMap("email", email)));

        return userMapper.toDTO(user);
    }

    @Override
    public Long getCurrentUserId() {

        User user = userRepository
                .findByEmail(
                        SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new ResourceNotFoundException(Collections.singletonMap(
                        "email",
                        SecurityContextHolder.getContext().getAuthentication().getName())));
        return user.getId();
    }

    @Override
    public UserDTO getCurrentLoginUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByEmail(email);

        return user.isPresent() ? userMapper.toDTO(user.get()) : null;
    }

    @Override
    public UserDTO create(UserCreationDTO userCreationDTO, MultipartFile fileAvatar, ERole eRole) {
        // check existing user info
        Map<String, Object> errors = new HashMap<String, Object>();
        if (userRepository.existsByEmail(userCreationDTO.getEmail())) {
            errors.put("email", userCreationDTO.getEmail());
        }

        if (errors.size() > 0) {
            throw new ConflictException(errors);
        }

        // set info for user
        User user = userMapper.toEntity(userCreationDTO);
        user.setPassword(passwordEncoder.encode(userCreationDTO.getPassword()));
        user.setAvatar(fileService.uploadFile(fileAvatar));
        // set default role and status
        user.setRole(roleRepository
                .findByName(eRole.toString())
                .orElseThrow(() ->
                        new InternalServerErrorException(Collections.singletonMap(eRole.toString(), "NOT EXISTS"))));
        user.setStatus(statusRepository
                .findByName(Estatus.Not_Active.toString())
                .orElseThrow(() -> new InternalServerErrorException(
                        Collections.singletonMap(Estatus.Not_Active.toString(), "NOT EXISTS IN"))));

        return userMapper.toDTO(userRepository.save(user));
    }

    @Override
    public UserDTO update(long id, UserProfileDTO userProfileDTO, MultipartFile fileAvatar) throws IOException {
        User oldUser = userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Collections.singletonMap("id", id)));

        // check existing user info in another one
        Map<String, Object> errors = new HashMap<String, Object>();
        if (userRepository.existsByIdNotAndEmail(id, userProfileDTO.getEmail())) {
            errors.put("email", userProfileDTO.getEmail());
        }
        if (errors.size() > 0) {
            throw new ConflictException(errors);
        }

        User updateUser = userMapper.toEntity(userProfileDTO);
        updateUser.setId(oldUser.getId());
        updateUser.setEmail(oldUser.getEmail());
        updateUser.setPassword(oldUser.getPassword());
        // check update file Avatar
        if (!StringUtils.equals(updateUser.getAvatar(), oldUser.getAvatar()) || (fileAvatar != null)) {
            //            fileService.deleteFile(oldUser.getAvatar());
            //            fileService.uploadFile(fileAvatar);
            //            updateUser.setAvatar(fileAvatar);
            updateUser.setAvatar(updateFile.uploadImage(fileAvatar));
        } else {
            updateUser.setAvatar(oldUser.getAvatar());
        }
        updateUser.setRole(oldUser.getRole());
        updateUser.setStatus(oldUser.getStatus());
        updateUser.setMailReceive(oldUser.isMailReceive());

        return userMapper.toDTO(userRepository.save(updateUser));
    }
}
