package com.dsmpear.main.controller;

import com.dsmpear.main.payload.request.EmailVerifyRequest;
import com.dsmpear.main.payload.request.NotificationRequest;
import com.dsmpear.main.service.email.EmailService;
import com.dsmpear.main.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {
    private final UserService userService;
    private final EmailService emailService;

    @GetMapping("/auth")
    public void authNumEmail(@Email String email) {
        emailService.sendAuthNumEmail(email);
    }

    @PutMapping("/auth")
    public void verifyAccount(@Valid EmailVerifyRequest request) {
        userService.verify(request);
    }

    @PostMapping("/notification")
    public void notification(NotificationRequest request) {
        emailService.sendNotificationEmail(request);
    }
}
