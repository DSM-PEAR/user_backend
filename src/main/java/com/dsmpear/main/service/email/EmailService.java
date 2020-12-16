package com.dsmpear.main.service.email;

import com.dsmpear.main.payload.request.NotificationRequest;

public interface EmailService {
    public void notificationEmail(NotificationRequest request, String secretKey);
    public void authNumEmail(String sendTo);
}
