package com.dsmpear.main.service.email;

import java.util.function.Function;

public enum EmailType {
    NOTIFICATION((emailParameter) -> {
        String content = emailParameter.getMailContent();

        content = content.replace("##NAME##", emailParameter.getName());
        return content.replace("##URL##", emailParameter.getNotificationUrl());
    } , "resources/notification_email"),

    EMAIL_AUTHORIZATION((emailParameter) -> {
        String content = emailParameter.getMailContent();

        content = content.replace("##NAME##", emailParameter.getName());
        return content.replace("##URL##", emailParameter.getAuthUrl());
    }, "resources/authorization_email");

    private final Function<EmailParameter, String> func;
    private final String address;

    EmailType(Function<EmailParameter, String> func, String address) {
        this.func = func;
        this.address = address;
    }

    public String changeContent(EmailParameter emailParameter) {
        return func.apply(emailParameter);
    }

    public String getAddress() {
        return this.address;
    }
}
