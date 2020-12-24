package com.dsmpear.main.service.email;

import com.dsmpear.main.entity.verifynumber.VerifyNumber;
import com.dsmpear.main.entity.verifynumber.VerifyNumberRepository;
import com.dsmpear.main.exceptions.EmailSendFailedException;
import com.dsmpear.main.exceptions.SecretKeyNotMatchedException;
import com.dsmpear.main.payload.request.NotificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.sargue.mailgun.Configuration;
import net.sargue.mailgun.Mail;
import net.sargue.mailgun.Response;
import net.sargue.mailgun.content.Body;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final PasswordEncoder passwordEncoder;
    private final VerifyNumberRepository numberRepository;

    @Value("${email.domain}")
    private String domain;

    @Value("${email.apiKey}")
    private String apiKey;

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

    public void sendNotificationEmail(NotificationRequest request) {
        Configuration configuration = new Configuration()
                .domain(domain)
                .apiKey(apiKey)
                .from("PEAR", "pear@dsm.hs.kr");

        Body builder = new Body(convertNotificationHtmlWithString(request), "");

        Response response = Mail.using(configuration)
                .to(request.getEmail())
                .subject("[PEAR] 승인 알림")
                .content(builder)
                .build()
                .send();

        if (!response.isOk())
            throw new EmailSendFailedException();
    }

    @Async
    public void sendAuthNumEmail(String sendTo) {
        String number = generateVerifyNumber();

        Configuration configuration = new Configuration()
                .domain(domain)
                .apiKey(apiKey)
                .from("PEAR", "pear@dsm.hs.kr");

        Body builder = new Body(convertNumberHtmlWithString(number), "");

        numberRepository.save(
                VerifyNumber.builder()
                        .email(sendTo)
                        .verifyNumber(number)
                        .build()
        );

        Response response = Mail.using(configuration)
                .to(sendTo)
                .subject("[PEAR] 인증번호 알림")
                .content(builder)
                .build()
                .send();

        if (!response.isOk())
            throw new EmailSendFailedException();
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
