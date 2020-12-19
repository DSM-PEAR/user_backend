package com.dsmpear.main.controller;

import com.dsmpear.main.payload.request.EmailVerifyRequest;
import com.dsmpear.main.payload.request.NotificationRequest;
import com.dsmpear.main.service.email.EmailService;
import com.dsmpear.main.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Email;

@RestController
@RequestMapping("/email")
@Validated
@RequiredArgsConstructor
public class EmailController {

    private final UserService userService;
    private final EmailService emailService;

    @GetMapping("/auth")
    public void authNumEmail(@RequestParam("email") @Email String email) {
        emailService.authNumEmail(email);
    }

    @PutMapping("/auth")
    public void verifyAccount(@RequestBody @Valid EmailVerifyRequest request) {
        userService.verify(request);
    }

    @PostMapping("/notification")
    public void notification(@RequestBody @Valid NotificationRequest request, @RequestHeader("Authorization") String secretKey) {
        emailService.notificationEmail(request, secretKey);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Object exception(Exception e) {
        return e.getMessage();
    }
}
