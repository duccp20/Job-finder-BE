package com.example.jobfinder.controller.user;


import com.example.jobfinder.constant.ApiURL;
import com.example.jobfinder.data.dto.request.mail.EmailRequest;
import com.example.jobfinder.data.dto.request.user.ResetPasswordByToken;
import com.example.jobfinder.data.dto.response.ResponseMessage;
import com.example.jobfinder.service.MailService;
import com.example.jobfinder.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.UnsupportedEncodingException;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(ApiURL.USER)
public class UserController {

    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;

    /**
     * A description of the sendTokenForgetPassword function.
     * @Author: Phước Đức
     * @param  emailRequest   description of the emailRequest parameter
     * @return                description of the return value
     * @throws MessagingException          description of the exception
     * @throws UnsupportedEncodingException description of the exception
     */
    @PostMapping("/forget-password")
    public ResponseEntity<?> sendTokenForgetPassword(@Valid @RequestBody EmailRequest emailRequest) throws MessagingException, UnsupportedEncodingException {
        return new ResponseEntity<>(
                mailService.sendTokenForgetPassword(emailRequest.getEmail()),
                HttpStatus.OK);
    }

    /**
     * Send a mail to activate the account.
     * @Author: Phước Đức
     * @param  emailRequest  the email request object containing the email address
     * @return               a ResponseEntity containing the result of the mail sending operation
     * @throws MessagingException             if there is an error while sending the mail
     * @throws UnsupportedEncodingException  if the encoding is not supported
     */
    @PostMapping("/active-account")
    public ResponseEntity<?> sendMailActive(@Valid @RequestBody EmailRequest emailRequest) throws MessagingException, UnsupportedEncodingException {
        return new ResponseEntity<>(
                mailService.sendMailActive(emailRequest.getEmail()),
                HttpStatus.OK);
    }

    /**
     * Retrieves the active account candidate based on the provided token.
     * @Author: Phước Đức
     * @param  token  the token used to activate the account
     * @return        the result of activating the account or a redirect view
     */
    @GetMapping("/active-forget-password")
    public Object activeAccountCandidate(@RequestParam(name = "token") String token) {
        try {
            return userService.activeForgetPassword(token);
        }
        catch (Exception e) {
            String redirectUrl = "http://localhost:3000/forgot-password/verify?status=fail";
            return new RedirectView(redirectUrl);
        }
    }

    /**
     * Resets the password using a token.
     * @Author: Phước Đức
     * @param  resetPasswordByTokenDTO  the DTO containing the reset password token
     * @return                          the response entity with the result of the password reset
     */
    @PostMapping("/reset-password-by-token")
    public ResponseEntity<?> resetPasswordByToken(
            @Valid @RequestBody ResetPasswordByToken resetPasswordByTokenDTO) {
        this.userService.resetPasswordByToken(resetPasswordByTokenDTO);

        return ResponseEntity.ok(new ResponseMessage(HttpServletResponse.SC_OK, "Đổi mật khẩu thành công!", null, null));
    }


    /**
     * Retrieves the user profile.
     * @Author: Phước Đức
     * @return Response entity with the user profile data.
     */
    @GetMapping
    public ResponseEntity<?> getUserProfile() {
        return ResponseEntity.ok(new ResponseMessage(HttpServletResponse.SC_OK, "Lấy profile thành công!", userService.getUserProfile(), null));
    }


}
