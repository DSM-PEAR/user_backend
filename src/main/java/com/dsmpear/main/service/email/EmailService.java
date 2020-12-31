package com.dsmpear.main.service.email;

import com.dsmpear.main.payload.request.NotificationRequest;

public interface EmailService {
    void sendNotificationEmail(NotificationRequest request, String secretKey);
    void sendAuthNumEmail(String sendTo);
}
