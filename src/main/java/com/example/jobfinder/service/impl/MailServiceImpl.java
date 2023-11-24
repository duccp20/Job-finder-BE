package com.example.jobfinder.service.impl;
import com.example.jobfinder.data.dto.response.ResponseMessage;
import com.example.jobfinder.data.dto.response.mail.MailResponse;
import com.example.jobfinder.data.entity.Token;
import com.example.jobfinder.data.entity.User;
import com.example.jobfinder.data.repository.UserRepository;
import com.example.jobfinder.exception.InternalServerErrorException;
import com.example.jobfinder.exception.ResourceNotFoundException;
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
import org.springframework.stereotype.Service;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

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

    @Value("${spring.mail.username}")
    private String sender;

    @Value("${domain.path:}")
    private String urlRedirect;


    @Override
    public void send(MailResponse mailResponse) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        messageHelper.setFrom(sender, mailResponse.getNamePost());
        messageHelper.setTo(mailResponse.getTo());

        switch (mailResponse.getTypeMail()) {
            case ConfirmMail -> {
                mailResponse.setSubject("Xác thực email cho tài khoản Jobsit.vn");
                userService.updateTokenActive(mailResponse.getTo(), mailResponse.getToken());
                mailResponse.createMailConfirm(urlRedirect, mailResponse.getToken());
                break;
            }
            case ForgotPassword -> {
                mailResponse.setSubject("Yêu cầu đổi mật khẩu tài khoản trên Jobsit.vn");
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
    public ResponseMessage sendMailActive(String email) throws MessagingException, UnsupportedEncodingException {
        User user = userRepository.findByEmail(email).orElseThrow(() ->  {
            throw new ResourceNotFoundException(Collections.singletonMap("email", email));
        });

        if (user.getStatus().getName().equals(Estatus.Active.toString()))
            throw new InternalServerErrorException(messageSource.getMessage("error.alreadyActive", null, null));

        Token token = tokenService.generateToken(user);
        MailResponse mailResponse = new MailResponse();
        mailResponse.setNamePost("job-finder");
        mailResponse.setNameReceive(user.getFirstName());
        mailResponse.setTo(email);// Set email to reset password! Get User by Email => Change Password
        mailResponse.setTypeMail(EMailType.ConfirmMail);
        mailResponse.setToken(token.getToken());
        this.send(mailResponse);
        return new ResponseMessage(HttpServletResponse.SC_OK, "SEND MAIL", null);
    }




//    @Override
//    public void sendMailWithAttachment(MailResponse details) throws MessagingException, UnsupportedEncodingException {
//
//        User user = userRepository.findByEmail(details.getNameReceive()).orElseThrow(() -> {
//                    throw new ResourceNotFoundException(Collections.singletonMap("email", details.getNameReceive()));
//                }
//        );
//
//
//        String urlDirect = url + "test thôi, chưa xác thực đâu";
//        Token token = tokenService.generateToken(user);
//        MimeMessage message = mailSender.createMimeMessage();
//        var messageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
//        messageHelper.setFrom(sender, details.getNamePost());
//        messageHelper.setTo(user.getEmail());
//        messageHelper.setText(new MailResponse().createMailConfirm(urlDirect, token.getToken()), true);
//        messageHelper.setSubject(details.getSubject());
//
//        // Sending mail
//        mailSender.send(message);
//    }
//
//    @Override
//    public Object sendMailActive(String email) throws MessagingException, UnsupportedEncodingException {
//
//        MailResponse mailResponse = MailResponse.builder()
//                .namePost("job-finder")
//                .subject("Xác thực email cho tài khoản Jobsit.vn")
//                .nameReceive(email)
//                .build();
//
//
//        this.sendMailWithAttachment(mailResponse);
//
//        return new ResponseEntity<>("Send mail Completed!", HttpStatus.OK);
//    }


}
