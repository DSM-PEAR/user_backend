package com.dsmpear.main.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class UserListResponse extends PageResponse {

    private List<UserResponse> userResponses;

    @Builder
    public UserListResponse(int totalElements, int totalPages, List<UserResponse> userResponses) {
        super(totalElements, totalPages);
        this.userResponses = userResponses;
    }

}
