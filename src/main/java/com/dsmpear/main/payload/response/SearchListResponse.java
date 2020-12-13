package com.dsmpear.main.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchListResponse extends PageResponse {

    private List<UserResponse> userResponses;

    @Builder
    public SearchListResponse(int totalElements, int totalPages, List<UserResponse> userResponses) {
        super(totalElements, totalPages);
        this.userResponses = userResponses;
    }
}
