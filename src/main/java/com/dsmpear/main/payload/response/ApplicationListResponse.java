package com.dsmpear.main.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ApplicationListResponse {

    private int totalElements;

    private int totalPages;

    private List applicationResponses;

}
