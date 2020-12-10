package com.dsmpear.main.service.email;

import com.dsmpear.main.entity.verifynumber.VerifyNumber;
import com.dsmpear.main.entity.verifynumber.VerifyNumberRepository;
import com.dsmpear.main.exceptions.EmailSendFailedException;
import com.dsmpear.main.payload.request.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final VerifyNumberRepository numberRepository;

    @Value("${spring.mail.host}")
    private String fromAddress;

    @Async
    @Override
    public void sendNotificationEmail(NotificationRequest request) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("이메일 알림");
    }

    @Async
    @Override
    public void sendAuthNumEmail(String sendTo) {
        String number = generateVerifyNumber();
        String mailContent;
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setSubject("[PEAR] 인증번호 안내 메일입니다.");
            message.setto(sendTo);
            Document doc = Jsoup.connect("resources/email.html").get();
            mailContent = doc.html();
            mailContent = mailContent.replace("{%code%}", number);
            message.setText(mailContent, "utf-8", "html");
            javaMailSender.send(mailContent);
        } catch (IOException | MessagingException e) {
            throw new EmailSendFailedException();
        }

        numberRepository.save(
                VerifyNumber.builder()
                    .email(sendTo)
                    .verifyNumber(number)
                    .build()
        );

        try {
            javaMailSender.send(message);
        } catch (MailException e) {
            throw new EmailSendFailedException();
        }
    }

    private String generateVerifyNumber() {
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        return Integer.toString(random.nextInt(1000000) % 1000000);
    }

}
