package com.example.jobfinder.service.impl;

import com.example.jobfinder.data.dto.request.mail.EmailRequest;
import com.example.jobfinder.data.dto.response.ResponseMessage;
import com.example.jobfinder.data.dto.response.mail.MailResponse;
import com.example.jobfinder.data.entity.Token;
import com.example.jobfinder.data.entity.User;
import com.example.jobfinder.data.repository.StatusRepository;
import com.example.jobfinder.data.repository.UserRepository;
import com.example.jobfinder.exception.InternalServerErrorException;
import com.example.jobfinder.exception.ResourceNotFoundException;
import com.example.jobfinder.security.jwt.JwtTokenUtils;
import com.example.jobfinder.service.MailService;
import com.example.jobfinder.service.TokenService;
import com.example.jobfinder.service.UserService;
import com.example.jobfinder.utils.enumeration.EMailType;
import com.example.jobfinder.utils.enumeration.Estatus;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Random;
import java.util.UUID;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private StatusRepository statusRepository;
    @Value("${spring.mail.username}")
    private String sender;

    @Value("${url.server.path}")
    private String urlRedirect;


    @Override
    public void send(MailResponse mailResponse) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        messageHelper.setFrom(sender, mailResponse.getNamePost());
        messageHelper.setTo(mailResponse.getTo());

        switch (mailResponse.getTypeMail()) {
            case ConfirmMail -> {
                mailResponse.setSubject("Xác thực email cho tài khoản DreamxWork");
                userService.updateTokenActive(mailResponse.getTo(), mailResponse.getToken());
                mailResponse.createMailConfirm(urlRedirect, mailResponse.getToken());
                break;
            }
            case ForgotPassword -> {
                mailResponse.setSubject("Yêu cầu đổi mật khẩu tài khoản trên DreamxWork");
                userService.updateTokenForgetPassword(mailResponse.getTo(), mailResponse.getToken());
                mailResponse.createMailForgotPassword(urlRedirect, mailResponse.getToken());
                break;
            }
            default -> throw new InternalServerErrorException("Type mail is incorect!");
        }
        messageHelper.setText(mailResponse.getMailTemplate(), true);
        messageHelper.setSubject(mailResponse.getSubject());

        //send mail
        mailSender.send(message);
    }

    @Override
    public ResponseMessage sendTokenForgetPassword(String email) throws MessagingException, UnsupportedEncodingException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> {
            throw new ResourceNotFoundException(Collections.singletonMap("email", email));
        });

        if (user.getStatus().getName().equals(Estatus.Not_Active.toString())) {
            throw new ResourceNotFoundException(Collections.singletonMap("email", email));
        }

//        String accessToken = this.generateActiveToken(user, emailRequest.getPassword());
        String accessToken = UUID.randomUUID().toString();
        MailResponse mailResponse = new MailResponse();
        mailResponse.setNamePost("job-finder");
        mailResponse.setNameReceive(user.getFirstName());
        mailResponse.setTo(email);

        mailResponse.setTypeMail(EMailType.ForgotPassword);
        mailResponse.setToken(accessToken);
        this.send(mailResponse);

        return new ResponseMessage(HttpServletResponse.SC_OK, "SEND MAIL", null, null);
    }


    @Override
    public ResponseMessage sendMailActive(String email) throws MessagingException, UnsupportedEncodingException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> {
            throw new ResourceNotFoundException(Collections.singletonMap("email", email));
        });

        if (user.getStatus().getName().equals(Estatus.Active.toString()))
            throw new InternalServerErrorException(
                    messageSource.getMessage("error.alreadyActive", null, null));


        String accessToken = this.generateActiveToken(user);
        MailResponse mailResponse = new MailResponse();
        mailResponse.setNamePost("dreamxwork");
        mailResponse.setNameReceive(user.getFirstName());
        mailResponse.setTo(email);
        mailResponse.setTypeMail(EMailType.ConfirmMail);
        mailResponse.setToken(accessToken);
        this.send(mailResponse);

        return new ResponseMessage(HttpServletResponse.SC_OK, "SEND MAIL", null, null);
    }

    private String generateActiveToken(User user) {

        String accessToken = jwtTokenUtils.generateToken(user);

        user.setTokenActive(accessToken);
        userRepository.save(user);

        return accessToken;
    }
}
