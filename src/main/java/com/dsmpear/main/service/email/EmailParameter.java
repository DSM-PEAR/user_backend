package com.dsmpear.main.service.email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @AllArgsConstructor @NoArgsConstructor @Builder
public class EmailParameter {
    private String mailContent;
    private String sendTo;
    private String name;
    private String authUrl;

    private String notificationUrl;

    public void setMailContent(String mailContent) {
        this.mailContent = mailContent;
    }
}
