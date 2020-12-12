package com.dsmpear.main.payload.request;

import lombok.Getter;

@Getter
public class NotificationRequest {

    private String boardId;

    private String email;

    private boolean isAccepted;
}
