package com.dsmpear.main.service.email;

import com.dsmpear.main.exceptions.EmailSendFailedException;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class EmailSender {
    @Value("${spring.mail.username}")
    private String sendFrom;

    private final JavaMailSender mailSender;

    public void sendEmail(EmailType emailType, EmailParameter emailParameter) throws EmailSendFailedException {
        try {
            Document doc = Jsoup.connect(emailType.getAddress()).get();
            emailParameter.setMailContent(doc.html());
        } catch (IOException e) {
            throw new EmailSendFailedException();
        }

        MimeMessagePreparator preparator = mimeMessage -> {
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            emailParameter.setMailContent(emailType.changeContent(emailParameter));

            message.setTo(emailParameter.getSendTo());
            message.setFrom(sendFrom);
            message.setText(emailParameter.getMailContent(), true);
        };

        try {
            mailSender.send(preparator);
        } catch (MailException e) {
            throw new EmailSendFailedException();
        }

    }
}
