package com.dsmpear.main.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ProfileReportListResponse extends PageResponse {

    private List<ProfileReportResponse> profileReportResponses;
    private List<MyProfileResponse> myProfileResponses;

    @Builder
    public ProfileReportListResponse(int totalElements, int totalPages,List<ProfileReportResponse> profileReportResponses, List<MyProfileResponse> myProfileResponses) {
        super(totalElements, totalPages);
        this.profileReportResponses = profileReportResponses;
        this.myProfileResponses = myProfileResponses;
    }
}
