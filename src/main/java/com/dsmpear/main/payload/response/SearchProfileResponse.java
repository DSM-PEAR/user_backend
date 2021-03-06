package com.dsmpear.main.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class SearchProfileResponse extends PageResponse {

    private List<UserResponse> userResponses;

    @Builder
    public SearchProfileResponse(Long totalElements, int totalPages, List<UserResponse> userResponses) {
        super(totalElements, totalPages);
        this.userResponses = userResponses;
    }

}
