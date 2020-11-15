package com.dsmpear.main.service.email;

import com.dsmpear.main.payload.request.NotificationRequest;

public interface EmailService {
    public void sendNotificationEmail(NotificationRequest request);
    public void sendAuthNumEmail(String sendTo);
}
