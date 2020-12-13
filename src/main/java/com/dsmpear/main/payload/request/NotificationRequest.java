package com.dsmpear.main.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class NotificationRequest {

    private String boardId;

    private String email;

    private boolean isAccepted;
}
