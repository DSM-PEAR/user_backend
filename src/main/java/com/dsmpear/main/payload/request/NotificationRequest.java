package com.dsmpear.main.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter @AllArgsConstructor
public class NotificationRequest {

    @NotNull
    private String boardId;

    @NotNull
    private String email;

    @NotNull
    private String body;

    @NotNull
    private boolean isAccepted;

    public NotificationRequest(String email, String body, boolean isAccepted) {
        this.email = email;
        this.body = body;
        this.isAccepted = isAccepted;
    }
}
