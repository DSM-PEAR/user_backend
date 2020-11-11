package com.dsmpear.main.service.email;

public interface EmailService {
    public void sendNotificationEmail();
    public void sendAuthNumEmail(String sendTo);
}
