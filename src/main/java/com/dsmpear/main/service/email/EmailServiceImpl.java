package com.dsmpear.main.service.email;

import com.dsmpear.main.exceptions.EmailSendFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;

    @Value("${mail.address}")
    private String fromAddress;

    @Async
    @Override
    public void sendNotificationEmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("");
    }

    @Async
    @Override
    public void sendAuthNumEmail(String sendTo) throws EmailSendFailedException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("회원가입 인증번호 안내 이메일입니다.");
        message.setFrom(fromAddress);
        message.setTo(sendTo);

        try {
            javaMailSender.send(message);
        } catch (MailException e) {
            throw new EmailSendFailedException();
        }
    }

    private String generateAuthNum() {
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        return Integer.toString(random.nextInt(1000000) % 1000000);
    }
}
