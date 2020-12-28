package com.dsmpear.main.service.email;

import com.dsmpear.main.entity.verifynumber.VerifyNumber;
import com.dsmpear.main.entity.verifynumber.VerifyNumberRepository;
import com.dsmpear.main.exceptions.EmailSendFailedException;
import com.dsmpear.main.exceptions.SecretKeyNotMatchedException;
import com.dsmpear.main.payload.request.NotificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;
    private final VerifyNumberRepository numberRepository;

    @Value("${server.url}")
    private String url;

    @Value("${secret.key}")
    private String secretKey;

    @Override
    public void notificationEmail(NotificationRequest request, String secretKey) {
        if (!passwordEncoder.matches(secretKey, this.secretKey))
            throw new SecretKeyNotMatchedException();

        sendNotificationEmail(request);
    }

    @Override
    public void authNumEmail(String sendTo) {
        sendAuthNumEmail(sendTo);
    }

    @Async
    public void sendNotificationEmail(NotificationRequest request) {

    }

    @Async
    public void sendAuthNumEmail(String sendTo) {
        String authNum = generateVerifyNumber();

        try {
            final MimeMessagePreparator preparator = mimeMessage -> {
                final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                helper.setFrom("pearavocat@gmail.com");
                helper.setTo(sendTo);
                helper.setSubject("PEAR 회원가입 인증번호 안내 메일입니다.");
                helper.setText(convertNumberHtmlWithString(authNum), true);
            };

            javaMailSender.send(preparator);

            numberRepository.save(
                    VerifyNumber.builder()
                    .email(sendTo)
                    .verifyNumber(authNum)
                    .build()
            );
        } catch (Exception e) {
            throw new EmailSendFailedException();
        }
    }

    private String generateVerifyNumber() {
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        return Integer.toString(random.nextInt(1000000) % 1000000);
    }

    @SneakyThrows
    private String convertNumberHtmlWithString(String code) {
        InputStream inputStream = new ClassPathResource("static/number_email.html").getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        StringBuilder stringBuilder = new StringBuilder();

        bufferedReader.lines()
                .filter(Objects::nonNull)
                .forEach(stringBuilder::append);

        return stringBuilder.toString().replace("{%code%}", code);
    }

    @SneakyThrows
    private String convertNotificationHtmlWithString(NotificationRequest request) {
        InputStream inputStream = new ClassPathResource("static/notification_email.html").getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        StringBuilder stringBuilder = new StringBuilder();

        bufferedReader.lines()
                .filter(Objects::nonNull)
                .forEach(stringBuilder::append);

        String body = stringBuilder.toString();

        if (request.getIsAccepted())
            body = body.replace("{{accepted}}", "승인");
        else {
            body = body.replace("{{accepted}}", "거부");
        }

        return body.replace("{{board_url}}", url + "report/" + request.getBoardId()).replace("{{body}}", request.getBody());
    }

}
