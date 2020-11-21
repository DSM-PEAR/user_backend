package com.dsmpear.main.payload.request;

import lombok.Getter;

@Getter
public class NotificationRequest {
    private String context;
    private String target_id;
}
