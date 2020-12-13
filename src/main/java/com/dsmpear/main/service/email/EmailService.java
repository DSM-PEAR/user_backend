package com.dsmpear.main.service.email;

import com.dsmpear.main.exceptions.EmailSendFailedException;
import com.dsmpear.main.payload.request.NotificationRequest;

public interface EmailService {
    public void sendNotificationEmail(NotificationRequest request, String secretKey);
    public void sendAuthNumEmail(String sendTo) throws EmailSendFailedException;
}
